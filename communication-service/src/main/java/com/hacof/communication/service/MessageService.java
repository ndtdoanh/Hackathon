package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.MessageRequest;
import com.hacof.communication.dto.response.MessageResponse;

public interface MessageService {
    MessageResponse createMessage(Long conversationId, MessageRequest request);

    MessageResponse getMessageById(Long messageId);

    List<MessageResponse> getMessagesByConversation(Long conversationId);
}
