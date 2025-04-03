package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.ConversationRequest;
import com.hacof.communication.dto.response.ConversationResponse;

public interface ConversationService {

    ConversationResponse createSingleConversation(ConversationRequest request);

    //    ConversationResponse createGroupConversation(ConversationRequest request);

    ConversationResponse getConversationById(Long id);

    List<ConversationResponse> getConversationsByUserId(Long userId);

    //    ConversationResponse updateConversation(Long id, ConversationUpdateRequest request);

    void deleteConversation(Long id);

    //    void addUserToConversation(Long conversationId, Long userId);

    //    void removeUserFromConversation(Long conversationId, Long userId);
}
