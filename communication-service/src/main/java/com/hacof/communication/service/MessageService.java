package com.hacof.communication.service;

import com.hacof.communication.dto.request.MessageCreateRequest;
import com.hacof.communication.dto.request.MessageUpdateRequest;
import com.hacof.communication.dto.response.MessageResponse;

public interface MessageService {
    MessageResponse createMessage(Long conversationId, MessageCreateRequest request);

    MessageResponse updateMessage(Long id, MessageUpdateRequest request);

    void deleteMessage(Long id);
}
