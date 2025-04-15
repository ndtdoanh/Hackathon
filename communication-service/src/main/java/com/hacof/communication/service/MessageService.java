package com.hacof.communication.service;

import com.hacof.communication.dto.request.MessageRequest;
import com.hacof.communication.dto.response.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse createMessage(Long conversationId, MessageRequest request);

    MessageResponse getMessageById(Long messageId);

    List<MessageResponse> getMessagesByConversation(Long conversationId);
}
