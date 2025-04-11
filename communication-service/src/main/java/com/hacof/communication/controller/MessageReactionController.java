package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hacof.communication.dto.ApiRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.MessageReactionRequest;
import com.hacof.communication.dto.response.MessageReactionResponse;
import com.hacof.communication.service.MessageReactionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/reactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageReactionController {
    MessageReactionService reactionService;
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/reactions/{messageId}")
    public void handleReaction(@Payload MessageReactionRequest request, @DestinationVariable Long messageId) {
        log.info("Message ID: {}", messageId);
        log.info("ReactionType: {}", request.getReactionType());

        MessageReactionResponse reactionResponse = reactionService.reactToMessage(messageId, request);

        log.info("Created reaction response: {}", reactionResponse);

        String destination = "/topic/messages/" + messageId;
        log.info("Sending to destination: {}", destination);
        messagingTemplate.convertAndSend(destination, reactionResponse);
    }

    @PostMapping("/{messageId}")
    //    @PreAuthorize("hasAuthority('REACT_TO_MESSAGE')")
    public ResponseEntity<ApiResponse<MessageReactionResponse>> reactToMessage(
            @PathVariable Long messageId, @RequestBody @Valid ApiRequest<MessageReactionRequest> request) {
        MessageReactionResponse reactionResponse = reactionService.reactToMessage(messageId, request.getData());
        ApiResponse<MessageReactionResponse> response = ApiResponse.<MessageReactionResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(reactionResponse)
                .message("Reaction added successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{reactionId}")
    //    @PreAuthorize("hasAuthority('DELETE_REACTION')")
    public ApiResponse<Void> deleteReaction(@PathVariable Long reactionId) {
        reactionService.removeReaction(reactionId);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Reaction has been removed")
                .build();
    }

    @GetMapping("/message/{messageId}")
    //    @PreAuthorize("hasAuthority('GET_REACTIONS_BY_MESSAGE')")
    public ApiResponse<List<MessageReactionResponse>> getReactionsByMessage(@PathVariable Long messageId) {
        return ApiResponse.<List<MessageReactionResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(reactionService.getReactionsByMessage(messageId))
                .message("Get all reactions for message")
                .build();
    }
}
