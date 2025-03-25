package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.MessageCreateRequest;
import com.hacof.communication.dto.request.MessageUpdateRequest;
import com.hacof.communication.dto.response.MessageResponse;

public interface MessageService {
    MessageResponse createMessage(Long conversationId, MessageCreateRequest request);

    List<MessageResponse> getMessagesByConversation(Long conversationId);

    MessageResponse updateMessage(Long messageId, MessageUpdateRequest request);

    void deleteMessage(Long messageId);
}
