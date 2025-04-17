package com.hacof.communication.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hacof.communication.dto.request.MessageRequest;
import com.hacof.communication.dto.response.MessageReactionResponse;
import com.hacof.communication.dto.response.MessageResponse;
import com.hacof.communication.entity.Conversation;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.entity.Message;
import com.hacof.communication.entity.User;
import com.hacof.communication.exception.AppException;
import com.hacof.communication.exception.ErrorCode;
import com.hacof.communication.repository.ConversationRepository;
import com.hacof.communication.repository.FileUrlRepository;
import com.hacof.communication.repository.MessageRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.MessageService;
import com.hacof.communication.util.AuditContext;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageServiceImpl implements MessageService {
    MessageRepository messageRepository;
    ConversationRepository conversationRepository;
    FileUrlRepository fileUrlRepository;
    UserRepository userRepository;

    @Override
    @Transactional
    public MessageResponse createMessage(Long conversationId, MessageRequest request) {
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        Message message = new Message();
        message.setConversation(conversation);
        message.setContent(request.getContent());
        message.setDeleted(false);

        User currentUser = userRepository
                .findById(AuditContext.getCurrentUser().getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        message.setCreatedBy(currentUser);
        message = messageRepository.save(message);

        List<FileUrl> fileUrls = fileUrlRepository.findAllByFileUrlInAndMessageIsNull(request.getFileUrls());

        for (FileUrl file : fileUrls) {
            file.setMessage(message);
        }

        fileUrlRepository.saveAll(fileUrls);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setId(String.valueOf(message.getId()));
        messageResponse.setConversationId(String.valueOf(conversationId));
        messageResponse.setContent(message.getContent());
        messageResponse.setCreatedAt(message.getCreatedDate());
        messageResponse.setCreatedByUserName(currentUser.getUsername());
        List<String> fileUrlsResponse =
                fileUrls.stream().map(FileUrl::getFileUrl).collect(Collectors.toList());
        messageResponse.setFileUrls(fileUrlsResponse);

        return messageResponse;
    }

    @Override
    public MessageResponse getMessageById(Long messageId) {
        Message message = messageRepository
                .findById(messageId)
                .orElseThrow(() -> new AppException(ErrorCode.MESSAGE_NOT_EXISTED));

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setId(String.valueOf(message.getId()));
        messageResponse.setConversationId(
                message.getConversation() != null
                        ? String.valueOf(message.getConversation().getId())
                        : null);
        messageResponse.setContent(message.getContent());

        if (message.getFileUrls() != null) {
            List<String> fileUrls =
                    message.getFileUrls().stream().map(FileUrl::getFileUrl).collect(Collectors.toList());
            messageResponse.setFileUrls(fileUrls);
        } else {
            messageResponse.setFileUrls(Collections.emptyList());
        }

        if (message.getReactions() != null) {
            List<MessageReactionResponse> reactions = message.getReactions().stream()
                    .map(reaction -> {
                        MessageReactionResponse response = new MessageReactionResponse();
                        response.setId(String.valueOf(reaction.getId()));
                        response.setMessageId(
                                String.valueOf(reaction.getMessage().getId()));
                        response.setReactionType(reaction.getReactionType());
                        response.setCreatedByUserName(
                                reaction.getCreatedBy() != null
                                        ? reaction.getCreatedBy().getUsername()
                                        : null);
                        response.setCreatedAt(reaction.getCreatedDate());
                        response.setUpdatedAt(reaction.getLastModifiedDate());
                        return response;
                    })
                    .collect(Collectors.toList());
            messageResponse.setReactions(reactions);
        } else {
            messageResponse.setReactions(Collections.emptyList());
        }

        messageResponse.setCreatedByUserName(
                message.getCreatedBy() != null ? message.getCreatedBy().getUsername() : null);
        messageResponse.setCreatedAt(message.getCreatedDate());
        messageResponse.setUpdatedAt(message.getLastModifiedDate());

        return messageResponse;
    }

    @Override
    public List<MessageResponse> getMessagesByConversation(Long conversationId) {
        List<Message> messages = messageRepository.findByConversationId(conversationId);

        return messages.stream()
                .map(message -> {
                    MessageResponse messageResponse = new MessageResponse();
                    messageResponse.setId(String.valueOf(message.getId()));
                    messageResponse.setConversationId(
                            message.getConversation() != null
                                    ? String.valueOf(message.getConversation().getId())
                                    : null);
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
                                    MessageReactionResponse response = new MessageReactionResponse();
                                    response.setId(String.valueOf(reaction.getId()));
                                    response.setMessageId(
                                            String.valueOf(reaction.getMessage().getId()));
                                    response.setReactionType(reaction.getReactionType());
                                    response.setCreatedByUserName(
                                            reaction.getCreatedBy() != null
                                                    ? reaction.getCreatedBy().getUsername()
                                                    : null);
                                    response.setCreatedAt(reaction.getCreatedDate());
                                    response.setUpdatedAt(reaction.getLastModifiedDate());
                                    return response;
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
    }
}
