package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.MessageReactionRequest;
import com.hacof.communication.dto.response.MessageReactionResponse;
import com.hacof.communication.entity.Message;
import com.hacof.communication.entity.MessageReaction;
import com.hacof.communication.entity.User;
import com.hacof.communication.exception.AppException;
import com.hacof.communication.exception.ErrorCode;
import com.hacof.communication.mapper.MessageReactionMapper;
import com.hacof.communication.repository.MessageReactionRepository;
import com.hacof.communication.repository.MessageRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.MessageReactionService;
import com.hacof.communication.util.SecurityUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageReactionServiceImpl implements MessageReactionService {
    MessageReactionRepository reactionRepository;
    MessageRepository messageRepository;
    MessageReactionMapper reactionMapper;
    SecurityUtil securityUtil;
    UserRepository userRepository;

    @Override
    public MessageReactionResponse reactToMessage(Long messageId, MessageReactionRequest request) {
        Message message = messageRepository
                .findById(messageId)
                .orElseThrow(() -> new AppException(ErrorCode.MESSAGE_NOT_EXISTED));

        User currentUser =
                securityUtil.getCurrentUser().orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Optional<MessageReaction> existingReaction =
                reactionRepository.findByMessageIdAndCreatedBy(messageId, currentUser);

        MessageReaction reaction = existingReaction.orElseGet(() -> {
            MessageReaction newReaction = reactionMapper.toMessageReaction(request);
            newReaction.setMessage(message);
            newReaction.setCreatedBy(currentUser);
            return newReaction;
        });

        reaction.setReactionType(request.getReactionType());
        reactionRepository.save(reaction);
        return reactionMapper.toMessageReactionResponse(reaction);
    }

    @Override
    public void removeReaction(Long reactionId) {
        if (!reactionRepository.existsById(reactionId)) {
            throw new AppException(ErrorCode.REACTION_NOT_EXISTED);
        }
        reactionRepository.deleteById(reactionId);
    }

    @Override
    public List<MessageReactionResponse> getReactionsByMessage(Long messageId) {

        boolean exists = messageRepository.existsById(messageId);
        if (!exists) {
            throw new AppException(ErrorCode.MESSAGE_NOT_EXISTED);
        }

        return reactionRepository.findByMessageId(messageId).stream()
                .map(reactionMapper::toMessageReactionResponse)
                .toList();
    }
}
