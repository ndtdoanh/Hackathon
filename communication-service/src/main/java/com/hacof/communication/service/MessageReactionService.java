package com.hacof.communication.service;

import com.hacof.communication.dto.request.MessageReactionRequest;

public interface MessageReactionService {
    void addReaction(Long messageId, MessageReactionRequest request);

    void removeReaction(Long reactionId);
}
