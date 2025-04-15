package com.hacof.communication.service;

import com.hacof.communication.dto.request.MessageReactionRequest;
import com.hacof.communication.dto.response.MessageReactionResponse;

import java.util.List;

public interface MessageReactionService {
    MessageReactionResponse reactToMessage(Long messageId, MessageReactionRequest request);

    void removeReaction(Long reactionId);

    List<MessageReactionResponse> getReactionsByMessage(Long messageId);
}
