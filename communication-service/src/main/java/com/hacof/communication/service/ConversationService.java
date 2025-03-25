package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ConversationCreateRequest;
import com.hacof.communication.dto.request.ConversationUpdateRequest;
import com.hacof.communication.dto.response.ConversationResponse;

public interface ConversationService {

    ConversationResponse createSingleConversation(ConversationCreateRequest request);

    ConversationResponse createGroupConversation(ConversationCreateRequest request);

    ConversationResponse getConversationById(Long id);

    List<ConversationResponse> getConversationsByUserId(Long userId);

    ConversationResponse updateConversation(Long id, ConversationUpdateRequest request);

    void deleteConversation(Long id);

    void addUserToConversation(Long conversationId, Long userId);

    void removeUserFromConversation(Long conversationId, Long userId);
}
