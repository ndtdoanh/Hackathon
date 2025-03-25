package com.hacof.communication.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hacof.communication.constant.ConversationType;
import com.hacof.communication.dto.request.*;
import com.hacof.communication.dto.response.ConversationResponse;
import com.hacof.communication.entity.Conversation;
import com.hacof.communication.entity.ConversationUser;
import com.hacof.communication.entity.User;
import com.hacof.communication.exception.AppException;
import com.hacof.communication.exception.ErrorCode;
import com.hacof.communication.mapper.ConversationMapper;
import com.hacof.communication.repository.ConversationRepository;
import com.hacof.communication.repository.ConversationUserRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.ConversationService;
import com.hacof.communication.util.AuditContext;
import com.hacof.communication.util.SecurityUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationServiceImpl implements ConversationService {
    ConversationRepository conversationRepository;
    UserRepository userRepository;
    ConversationUserRepository conversationUserRepository;
    ConversationMapper conversationMapper;
    SecurityUtil securityUtil;

    @Override
    @Transactional
    public ConversationResponse createSingleConversation(ConversationCreateRequest request) {
        Set<Long> userIds = request.getUserIds();
        if (userIds == null || userIds.size() != 1) {
            throw new AppException(ErrorCode.INVALID_CONVERSATION_REQUEST);
        }

        Long createdById = AuditContext.getCurrentUser().getId();
        Long otherUserId = userIds.iterator().next();

        if (createdById.equals(otherUserId)) {
            throw new AppException(ErrorCode.CANNOT_CREATE_CONVERSATION_WITH_SELF);
        }

        if (!userRepository.existsById(otherUserId)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        boolean exists = conversationRepository.existsSingleConversationBetweenUsers(createdById, otherUserId);
        if (exists) {
            throw new AppException(ErrorCode.CONVERSATION_ALREADY_EXISTS);
        }

        Conversation conversation = new Conversation();
        conversation.setType(ConversationType.PRIVATE);
        conversation.setTeam(null);
        conversation.setName(null);

        Set<ConversationUser> conversationUsers = new HashSet<>();
        conversationUsers.add(new ConversationUser(userRepository.getReferenceById(createdById), conversation));
        conversationUsers.add(new ConversationUser(userRepository.getReferenceById(otherUserId), conversation));

        conversation.setConversationUsers(conversationUsers);
        conversation = conversationRepository.save(conversation);

        return conversationMapper.toConversationResponse(conversation);
    }

    @Override
    @Transactional
    public ConversationResponse createGroupConversation(ConversationCreateRequest request) {
        Set<Long> userIds = request.getUserIds();
        if (userIds == null || userIds.size() < 2) {
            throw new AppException(ErrorCode.INVALID_GROUP_CONVERSATION_REQUEST);
        }

        Long createdById = AuditContext.getCurrentUser().getId();

        List<Long> existingUserIds =
                userRepository.findAllById(userIds).stream().map(User::getId).toList();

        if (existingUserIds.size() != userIds.size()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        Conversation conversation = new Conversation();
        conversation.setType(ConversationType.PRIVATE);
        conversation.setTeam(null);
        conversation.setName(request.getName());

        Set<ConversationUser> conversationUsers = new HashSet<>();
        conversationUsers.add(new ConversationUser(userRepository.getReferenceById(createdById), conversation));

        for (Long userId : userIds) {
            conversationUsers.add(new ConversationUser(userRepository.getReferenceById(userId), conversation));
        }

        conversation.setConversationUsers(conversationUsers);
        conversation = conversationRepository.save(conversation);

        return conversationMapper.toConversationResponse(conversation);
    }

    @Override
    public ConversationResponse getConversationById(Long id) {
        return conversationMapper.toConversationResponse(conversationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED)));
    }

    @Override
    public List<ConversationResponse> getConversationsByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return conversationRepository.findConversationsByUserId(userId).stream()
                .map(conversationMapper::toConversationResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ConversationResponse updateConversation(Long id, ConversationUpdateRequest request) {
        Conversation conversation = conversationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        if (conversation.getConversationUsers().size() <= 2) {
            throw new AppException(ErrorCode.CANNOT_UPDATE_SINGLE_CONVERSATION);
        }

        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        if (!Objects.equals(conversation.getCreatedBy().getId(), currentUser.getId())) {
            throw new AppException(ErrorCode.NOT_CONVERSATION_CREATOR);
        }

        conversation.setName(request.getName());
        conversation = conversationRepository.save(conversation);

        return conversationMapper.toConversationResponse(conversation);
    }

    @Override
    @Transactional
    public void deleteConversation(Long id) {
        Conversation conversation = conversationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        if (!Objects.equals(conversation.getCreatedBy().getId(), currentUser.getId())) {
            throw new AppException(ErrorCode.NOT_CONVERSATION_CREATOR);
        }

        conversationRepository.delete(conversation);
    }

    @Override
    @Transactional
    public void addUserToConversation(Long conversationId, Long userId) {
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        if (conversation.getConversationUsers().size() <= 2) {
            throw new AppException(ErrorCode.CANNOT_ADD_USER_TO_SINGLE_CONVERSATION);
        }

        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        if (!Objects.equals(conversation.getCreatedBy().getId(), currentUser.getId())) {
            throw new AppException(ErrorCode.NOT_CONVERSATION_CREATOR);
        }

        boolean userExists = conversation.getConversationUsers().stream()
                .anyMatch(cu -> Objects.equals(cu.getUser().getId(), userId));
        if (userExists) {
            throw new AppException(ErrorCode.USER_ALREADY_IN_CONVERSATION);
        }

        User userToAdd =
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        ConversationUser conversationUser = new ConversationUser();
        conversationUser.setUser(userToAdd);
        conversationUser.setConversation(conversation);
        conversationUser.setDeleted(false);

        conversationUserRepository.save(conversationUser);
    }

    @Override
    @Transactional
    public void removeUserFromConversation(Long conversationId, Long userId) {
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        if (conversation.getConversationUsers().size() <= 2) {
            throw new AppException(ErrorCode.CANNOT_REMOVE_USER_FROM_SINGLE_CONVERSATION);
        }

        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        if (!Objects.equals(conversation.getCreatedBy().getId(), currentUser.getId())) {
            throw new AppException(ErrorCode.NOT_CONVERSATION_CREATOR);
        }

        ConversationUser conversationUserToRemove = conversation.getConversationUsers().stream()
                .filter(cu -> Objects.equals(cu.getUser().getId(), userId))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_IN_CONVERSATION));

        conversation.getConversationUsers().remove(conversationUserToRemove);

        conversationUserRepository.delete(conversationUserToRemove);
    }
}
