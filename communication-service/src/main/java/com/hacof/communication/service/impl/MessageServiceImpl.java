package com.hacof.communication.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.MessageCreateRequest;
import com.hacof.communication.dto.request.MessageUpdateRequest;
import com.hacof.communication.dto.response.MessageResponse;
import com.hacof.communication.entity.Conversation;
import com.hacof.communication.entity.Message;
import com.hacof.communication.exception.AppException;
import com.hacof.communication.exception.ErrorCode;
import com.hacof.communication.mapper.MessageMapper;
import com.hacof.communication.repository.ConversationRepository;
import com.hacof.communication.repository.MessageRepository;
import com.hacof.communication.service.MessageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageServiceImpl implements MessageService {
    MessageRepository messageRepository;
    ConversationRepository conversationRepository;
    MessageMapper messageMapper;

    @Override
    public MessageResponse createMessage(Long conversationId, MessageCreateRequest request) {
        Conversation conversation = conversationRepository
                .findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        Message message = messageMapper.toMessage(request);
        message.setConversation(conversation);
        messageRepository.save(message);

        return messageMapper.toMessageResponse(message);
    }

    @Override
    public List<MessageResponse> getMessagesByConversation(Long conversationId) {
        return messageRepository.findByConversationId(conversationId).stream()
                .map(messageMapper::toMessageResponse)
                .toList();
    }

    @Override
    public MessageResponse updateMessage(Long messageId, MessageUpdateRequest request) {
        Message message = messageRepository
                .findById(messageId)
                .orElseThrow(() -> new AppException(ErrorCode.MESSAGE_NOT_EXISTED));

        messageMapper.updateMessageFromRequest(request, message);
        message = messageRepository.save(message);

        return messageMapper.toMessageResponse(message);
    }

    @Override
    public void deleteMessage(Long messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new AppException(ErrorCode.MESSAGE_NOT_EXISTED);
        }
        messageRepository.deleteById(messageId);
    }
}
