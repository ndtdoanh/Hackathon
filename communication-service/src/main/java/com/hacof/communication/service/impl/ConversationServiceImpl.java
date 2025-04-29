package com.hacof.communication.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hacof.communication.constant.ConversationType;
import com.hacof.communication.dto.request.ConversationRequest;
import com.hacof.communication.dto.response.ConversationResponse;
import com.hacof.communication.dto.response.ConversationUserResponse;
import com.hacof.communication.dto.response.MessageReactionResponse;
import com.hacof.communication.dto.response.MessageResponse;
import com.hacof.communication.entity.Conversation;
import com.hacof.communication.entity.ConversationUser;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.entity.User;
import com.hacof.communication.exception.AppException;
import com.hacof.communication.exception.ErrorCode;
import com.hacof.communication.repository.ConversationRepository;
import com.hacof.communication.repository.MessageRepository;
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
    SecurityUtil securityUtil;
    MessageRepository messageRepository;

    @Override
    @Transactional
    public ConversationResponse createSingleConversation(ConversationRequest request) {
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_CONVERSATION_REQUEST);
        }

        Long otherUserId;
        try {
            otherUserId = Long.parseLong(request.getUserId());
        } catch (NumberFormatException e) {
            throw new AppException(ErrorCode.INVALID_CONVERSATION_REQUEST);
        }

        Long createdById = AuditContext.getCurrentUser().getId();

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

        User otherUser = userRepository.getReferenceById(otherUserId);
        String otherFullName = otherUser.getFirstName() + " " + otherUser.getLastName();
        conversation.setName(otherFullName);

        Set<ConversationUser> conversationUsers = new HashSet<>();
        ConversationUser creatorUser = new ConversationUser(userRepository.getReferenceById(createdById), conversation);
        ConversationUser otherUserConversation =
                new ConversationUser(userRepository.getReferenceById(otherUserId), conversation);
        conversationUsers.add(creatorUser);
        conversationUsers.add(otherUserConversation);

        conversation.setConversationUsers(conversationUsers);
        conversation = conversationRepository.save(conversation);

        ConversationResponse response = new ConversationResponse();
        response.setId(String.valueOf(conversation.getId()));
        response.setType(conversation.getType());
        response.setName(conversation.getName());

        String avatarUrl = otherUser.getAvatarUrl();
        response.setAvatarUrl(avatarUrl);

        final String conversationId = String.valueOf(conversation.getId());

        Set<ConversationUserResponse> conversationUserResponses = conversation.getConversationUsers().stream()
                .map(conversationUser -> {
                    ConversationUserResponse userResponse = new ConversationUserResponse();
                    userResponse.setId(String.valueOf(conversationUser.getId()));
                    userResponse.setConversationId(conversationId);
                    userResponse.setUserId(
                            String.valueOf(conversationUser.getUser().getId()));
                    userResponse.setDeleted(conversationUser.isDeleted());
                    userResponse.setDeletedByUserName(
                            conversationUser.getDeletedBy() != null
                                    ? conversationUser.getDeletedBy().getUsername()
                                    : null);
                    userResponse.setCreatedAt(conversationUser.getCreatedDate());
                    userResponse.setUpdatedAt(conversationUser.getLastModifiedDate());

                    User user = conversationUser.getUser();
                    if (user != null) {
                        userResponse.setFirstName(user.getFirstName());
                        userResponse.setLastName(user.getLastName());
                        userResponse.setAvatarUrl(user.getAvatarUrl());
                    }

                    return userResponse;
                })
                .collect(Collectors.toSet());
        response.setConversationUsers(conversationUserResponses);

        List<MessageResponse> messages = messageRepository.findByConversationId(conversation.getId()).stream()
                .map(message -> {
                    MessageResponse messageResponse = new MessageResponse();
                    messageResponse.setId(String.valueOf(message.getId()));
                    messageResponse.setConversationId(conversationId);
                    messageResponse.setContent(message.getContent());

                    if (message.getFileUrls() != null) {
                        List<String> fileUrls = message.getFileUrls().stream()
                                .map(FileUrl::getFileUrl)
                                .collect(Collectors.toList());
                        messageResponse.setFileUrls(fileUrls);
                    } else {
                        messageResponse.setFileUrls(Collections.emptyList());
                    }

                    if (message.getReactions() != null) {
                        List<MessageReactionResponse> reactions = message.getReactions().stream()
                                .map(reaction -> {
                                    MessageReactionResponse reactionResponse = new MessageReactionResponse();
                                    reactionResponse.setId(String.valueOf(reaction.getId()));
                                    reactionResponse.setMessageId(
                                            String.valueOf(reaction.getMessage().getId()));
                                    reactionResponse.setReactionType(reaction.getReactionType());
                                    reactionResponse.setCreatedByUserName(
                                            reaction.getCreatedBy() != null
                                                    ? reaction.getCreatedBy().getUsername()
                                                    : null);
                                    reactionResponse.setCreatedAt(reaction.getCreatedDate());
                                    reactionResponse.setUpdatedAt(reaction.getLastModifiedDate());
                                    return reactionResponse;
                                })
                                .collect(Collectors.toList());
                        messageResponse.setReactions(reactions);
                    } else {
                        messageResponse.setReactions(Collections.emptyList());
                    }

                    messageResponse.setCreatedByUserName(
                            message.getCreatedBy() != null
                                    ? message.getCreatedBy().getUsername()
                                    : null);
                    messageResponse.setCreatedAt(message.getCreatedDate());
                    messageResponse.setUpdatedAt(message.getLastModifiedDate());

                    return messageResponse;
                })
                .collect(Collectors.toList());
        response.setMessages(messages);

        response.setCreatedAt(conversation.getCreatedDate());
        response.setUpdatedAt(conversation.getLastModifiedDate());
        response.setCreatedByUserName(
                conversation.getCreatedBy() != null
                        ? conversation.getCreatedBy().getUsername()
                        : null);

        return response;
    }

    //    @Override
    //    @Transactional
    //    public ConversationResponse createGroupConversation(ConversationRequest request) {
    //        Set<Long> userIds = request.getUserIds();
    //        if (userIds == null || userIds.size() < 2) {
    //            throw new AppException(ErrorCode.INVALID_GROUP_CONVERSATION_REQUEST);
    //        }
    //
    //        Long createdById = AuditContext.getCurrentUser().getId();
    //
    //        List<Long> existingUserIds =
    //                userRepository.findAllById(userIds).stream().map(User::getId).toList();
    //
    //        if (existingUserIds.size() != userIds.size()) {
    //            throw new AppException(ErrorCode.USER_NOT_EXISTED);
    //        }
    //
    //        Conversation conversation = new Conversation();
    //        conversation.setType(ConversationType.PRIVATE);
    //        conversation.setName(request.getName());
    //
    //        Set<ConversationUser> conversationUsers = new HashSet<>();
    //        conversationUsers.add(new ConversationUser(userRepository.getReferenceById(createdById), conversation));
    //
    //        for (Long userId : userIds) {
    //            conversationUsers.add(new ConversationUser(userRepository.getReferenceById(userId), conversation));
    //        }
    //
    //        conversation.setConversationUsers(conversationUsers);
    //        conversation = conversationRepository.save(conversation);
    //
    //        return conversationMapper.toConversationResponse(conversation);
    //    }

    @Override
    public ConversationResponse getConversationById(Long id) {
        Conversation conversation = conversationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        ConversationResponse response = new ConversationResponse();
        response.setId(String.valueOf(conversation.getId()));
        response.setType(conversation.getType());

        if (conversation.getName() != null && !conversation.getName().isEmpty()) {
            response.setName(conversation.getName());
        } else {
            if (conversation.getType() == ConversationType.PRIVATE) {
                Long currentUserId = AuditContext.getCurrentUser().getId();
                Optional<ConversationUser> otherUser = conversation.getConversationUsers().stream()
                        .filter(cu -> cu.getUser().getId() != currentUserId)
                        .findFirst();

                String name = otherUser
                        .map(cu ->
                                cu.getUser().getFirstName() + " " + cu.getUser().getLastName())
                        .orElse("Unknown");
                response.setName(name);
            } else {
                response.setName(conversation.getName());
            }
        }

        Set<ConversationUserResponse> conversationUserResponses = conversation.getConversationUsers().stream()
                .map(conversationUser -> {
                    ConversationUserResponse userResponse = new ConversationUserResponse();
                    userResponse.setId(String.valueOf(conversationUser.getId()));
                    userResponse.setConversationId(String.valueOf(conversation.getId()));
                    userResponse.setUserId(
                            String.valueOf(conversationUser.getUser().getId()));
                    userResponse.setDeleted(conversationUser.isDeleted());
                    userResponse.setDeletedByUserName(
                            conversationUser.getDeletedBy() != null
                                    ? conversationUser.getDeletedBy().getUsername()
                                    : null);
                    userResponse.setCreatedAt(conversationUser.getCreatedDate());
                    userResponse.setUpdatedAt(conversationUser.getLastModifiedDate());

                    User user = conversationUser.getUser();
                    if (user != null) {
                        userResponse.setFirstName(user.getFirstName());
                        userResponse.setLastName(user.getLastName());
                        userResponse.setAvatarUrl(user.getAvatarUrl());
                    }

                    return userResponse;
                })
                .collect(Collectors.toSet());
        response.setConversationUsers(conversationUserResponses);

        List<MessageResponse> messages = messageRepository.findByConversationId(conversation.getId()).stream()
                .map(message -> {
                    MessageResponse messageResponse = new MessageResponse();
                    messageResponse.setId(String.valueOf(message.getId()));
                    messageResponse.setConversationId(String.valueOf(conversation.getId()));
                    messageResponse.setContent(message.getContent());

                    if (message.getFileUrls() != null) {
                        List<String> fileUrls = message.getFileUrls().stream()
                                .map(FileUrl::getFileUrl)
                                .collect(Collectors.toList());
                        messageResponse.setFileUrls(fileUrls);
                    } else {
                        messageResponse.setFileUrls(Collections.emptyList());
                    }

                    if (message.getReactions() != null) {
                        List<MessageReactionResponse> reactions = message.getReactions().stream()
                                .map(reaction -> {
                                    MessageReactionResponse reactionResponse = new MessageReactionResponse();
                                    reactionResponse.setId(String.valueOf(reaction.getId()));
                                    reactionResponse.setMessageId(
                                            String.valueOf(reaction.getMessage().getId()));
                                    reactionResponse.setReactionType(reaction.getReactionType());
                                    reactionResponse.setCreatedByUserName(
                                            reaction.getCreatedBy() != null
                                                    ? reaction.getCreatedBy().getUsername()
                                                    : null);
                                    reactionResponse.setCreatedAt(reaction.getCreatedDate());
                                    reactionResponse.setUpdatedAt(reaction.getLastModifiedDate());
                                    return reactionResponse;
                                })
                                .collect(Collectors.toList());
                        messageResponse.setReactions(reactions);
                    } else {
                        messageResponse.setReactions(Collections.emptyList());
                    }

                    messageResponse.setCreatedByUserName(
                            message.getCreatedBy() != null
                                    ? message.getCreatedBy().getUsername()
                                    : null);
                    messageResponse.setCreatedAt(message.getCreatedDate());
                    messageResponse.setUpdatedAt(message.getLastModifiedDate());

                    return messageResponse;
                })
                .collect(Collectors.toList());
        response.setMessages(messages);

        response.setCreatedAt(conversation.getCreatedDate());
        response.setUpdatedAt(conversation.getLastModifiedDate());
        response.setCreatedByUserName(
                conversation.getCreatedBy() != null
                        ? conversation.getCreatedBy().getUsername()
                        : null);

        return response;
    }

    @Override
    public List<ConversationResponse> getConversationsByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return conversationRepository.findConversationsByUserId(userId).stream()
                .map(conversation -> {
                    ConversationResponse response = new ConversationResponse();
                    response.setId(String.valueOf(conversation.getId()));
                    response.setType(conversation.getType());

                    if (conversation.getName() != null
                            && !conversation.getName().isEmpty()) {
                        response.setName(conversation.getName());
                    } else {
                        if (conversation.getType() == ConversationType.PRIVATE) {
                            Long currentUserId = AuditContext.getCurrentUser().getId();
                            Optional<ConversationUser> otherUser = conversation.getConversationUsers().stream()
                                    .filter(cu -> cu.getUser().getId() != currentUserId)
                                    .findFirst();

                            String name = otherUser
                                    .map(cu -> cu.getUser().getFirstName() + " "
                                            + cu.getUser().getLastName())
                                    .orElse("Unknown");
                            response.setName(name);
                        } else {
                            response.setName(conversation.getName());
                        }
                    }

                    Set<ConversationUserResponse> conversationUserResponses =
                            conversation.getConversationUsers().stream()
                                    .map(conversationUser -> {
                                        ConversationUserResponse userResponse = new ConversationUserResponse();
                                        userResponse.setId(String.valueOf(conversationUser.getId()));
                                        userResponse.setConversationId(String.valueOf(conversation.getId()));
                                        userResponse.setUserId(String.valueOf(
                                                conversationUser.getUser().getId()));
                                        userResponse.setDeleted(conversationUser.isDeleted());
                                        userResponse.setDeletedByUserName(
                                                conversationUser.getDeletedBy() != null
                                                        ? conversationUser
                                                                .getDeletedBy()
                                                                .getUsername()
                                                        : null);
                                        userResponse.setCreatedAt(conversationUser.getCreatedDate());
                                        userResponse.setUpdatedAt(conversationUser.getLastModifiedDate());

                                        User user = conversationUser.getUser();
                                        if (user != null) {
                                            userResponse.setFirstName(user.getFirstName());
                                            userResponse.setLastName(user.getLastName());
                                            userResponse.setAvatarUrl(user.getAvatarUrl());
                                        }

                                        return userResponse;
                                    })
                                    .collect(Collectors.toSet());
                    response.setConversationUsers(conversationUserResponses);

                    List<MessageResponse> messages =
                            messageRepository.findByConversationId(conversation.getId()).stream()
                                    .map(message -> {
                                        MessageResponse messageResponse = new MessageResponse();
                                        messageResponse.setId(String.valueOf(message.getId()));
                                        messageResponse.setConversationId(String.valueOf(conversation.getId()));
                                        messageResponse.setContent(message.getContent());

                                        if (message.getFileUrls() != null) {
                                            List<String> fileUrls = message.getFileUrls().stream()
                                                    .map(FileUrl::getFileUrl)
                                                    .collect(Collectors.toList());
                                            messageResponse.setFileUrls(fileUrls);
                                        } else {
                                            messageResponse.setFileUrls(Collections.emptyList());
                                        }

                                        if (message.getReactions() != null) {
                                            List<MessageReactionResponse> reactions = message.getReactions().stream()
                                                    .map(reaction -> {
                                                        MessageReactionResponse reactionResponse =
                                                                new MessageReactionResponse();
                                                        reactionResponse.setId(String.valueOf(reaction.getId()));
                                                        reactionResponse.setMessageId(
                                                                String.valueOf(reaction.getMessage()
                                                                        .getId()));
                                                        reactionResponse.setReactionType(reaction.getReactionType());
                                                        reactionResponse.setCreatedByUserName(
                                                                reaction.getCreatedBy() != null
                                                                        ? reaction.getCreatedBy()
                                                                                .getUsername()
                                                                        : null);
                                                        reactionResponse.setCreatedAt(reaction.getCreatedDate());
                                                        reactionResponse.setUpdatedAt(reaction.getLastModifiedDate());
                                                        return reactionResponse;
                                                    })
                                                    .collect(Collectors.toList());
                                            messageResponse.setReactions(reactions);
                                        } else {
                                            messageResponse.setReactions(Collections.emptyList());
                                        }

                                        messageResponse.setCreatedByUserName(
                                                message.getCreatedBy() != null
                                                        ? message.getCreatedBy().getUsername()
                                                        : null);
                                        messageResponse.setCreatedAt(message.getCreatedDate());
                                        messageResponse.setUpdatedAt(message.getLastModifiedDate());

                                        return messageResponse;
                                    })
                                    .collect(Collectors.toList());
                    response.setMessages(messages);

                    response.setCreatedAt(conversation.getCreatedDate());
                    response.setUpdatedAt(conversation.getLastModifiedDate());
                    response.setCreatedByUserName(
                            conversation.getCreatedBy() != null
                                    ? conversation.getCreatedBy().getUsername()
                                    : null);

                    return response;
                })
                .collect(Collectors.toList());
    }

    //    @Override
    //    @Transactional
    //    public ConversationResponse updateConversation(Long id, ConversationUpdateRequest request) {
    //        Conversation conversation = conversationRepository
    //                .findById(id)
    //                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
    //
    //        if (conversation.getConversationUsers().size() <= 2) {
    //            throw new AppException(ErrorCode.CANNOT_UPDATE_SINGLE_CONVERSATION);
    //        }
    //
    //        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new
    // AppException(ErrorCode.UNAUTHORIZED));
    //
    //        if (!Objects.equals(conversation.getCreatedBy().getId(), currentUser.getId())) {
    //            throw new AppException(ErrorCode.NOT_CONVERSATION_CREATOR);
    //        }
    //
    //        conversation.setName(request.getName());
    //        conversation = conversationRepository.save(conversation);
    //
    //        return conversationMapper.toConversationResponse(conversation);
    //    }

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

    //    @Override
    //    @Transactional
    //    public void addUserToConversation(Long conversationId, Long userId) {
    //        Conversation conversation = conversationRepository
    //                .findById(conversationId)
    //                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
    //
    //        if (conversation.getConversationUsers().size() <= 2) {
    //            throw new AppException(ErrorCode.CANNOT_ADD_USER_TO_SINGLE_CONVERSATION);
    //        }
    //
    //        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new
    // AppException(ErrorCode.UNAUTHORIZED));
    //
    //        if (!Objects.equals(conversation.getCreatedBy().getId(), currentUser.getId())) {
    //            throw new AppException(ErrorCode.NOT_CONVERSATION_CREATOR);
    //        }
    //
    //        boolean userExists = conversation.getConversationUsers().stream()
    //                .anyMatch(cu -> Objects.equals(cu.getUser().getId(), userId));
    //        if (userExists) {
    //            throw new AppException(ErrorCode.USER_ALREADY_IN_CONVERSATION);
    //        }
    //
    //        User userToAdd =
    //                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    //
    //        ConversationUser conversationUser = new ConversationUser();
    //        conversationUser.setUser(userToAdd);
    //        conversationUser.setConversation(conversation);
    //        conversationUser.setDeleted(false);
    //
    //        conversationUserRepository.save(conversationUser);
    //    }

    //    @Override
    //    @Transactional
    //    public void removeUserFromConversation(Long conversationId, Long userId) {
    //        Conversation conversation = conversationRepository
    //                .findById(conversationId)
    //                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
    //
    //        if (conversation.getConversationUsers().size() <= 2) {
    //            throw new AppException(ErrorCode.CANNOT_REMOVE_USER_FROM_SINGLE_CONVERSATION);
    //        }
    //
    //        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new
    // AppException(ErrorCode.UNAUTHORIZED));
    //
    //        if (!Objects.equals(conversation.getCreatedBy().getId(), currentUser.getId())) {
    //            throw new AppException(ErrorCode.NOT_CONVERSATION_CREATOR);
    //        }
    //
    //        ConversationUser conversationUserToRemove = conversation.getConversationUsers().stream()
    //                .filter(cu -> Objects.equals(cu.getUser().getId(), userId))
    //                .findFirst()
    //                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_IN_CONVERSATION));
    //
    //        conversation.getConversationUsers().remove(conversationUserToRemove);
    //
    //        conversationUserRepository.delete(conversationUserToRemove);
    //    }
}
