package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.communication.dto.ApiRequest;
import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.MessageRequest;
import com.hacof.communication.dto.response.MessageResponse;
import com.hacof.communication.service.MessageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    MessageService messageService;
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{conversationId}/{username}")
    public void handleWebSocketMessage(
            @Payload MessageRequest request,
            @DestinationVariable Long conversationId,
            @DestinationVariable String username) {
        log.info("Received WebSocket message for conversation: {}", conversationId);
        log.info("Message content: {}", request.getContent());

        MessageResponse messageResponse = messageService.getMessageById(request.getId());
        messageResponse.setCreatedByUserName(username);

        String destination = "/topic/conversations/" + conversationId;
        log.info("Sending to destination: {}", destination);
        messagingTemplate.convertAndSend(destination, messageResponse);
    }

    @PostMapping("/{conversationId}")
    public ResponseEntity<ApiResponse<MessageResponse>> createMessage(
            @PathVariable Long conversationId, @RequestBody @Valid ApiRequest<MessageRequest> request) {
        MessageResponse messageResponse = messageService.createMessage(conversationId, request.getData());
        ApiResponse<MessageResponse> response = ApiResponse.<MessageResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(messageResponse)
                .message("Message created successfully")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{messageId}")
    // @PreAuthorize("hasAuthority('GET_MESSAGE')")
    public ApiResponse<MessageResponse> getMessageById(@PathVariable Long messageId) {
        MessageResponse messageResponse = messageService.getMessageById(messageId);
        return ApiResponse.<MessageResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(messageResponse)
                .message("Message retrieved successfully")
                .build();
    }

    @GetMapping("/conversation/{conversationId}")
    //    @PreAuthorize("hasAuthority('GET_MESSAGES')")
    public ApiResponse<List<MessageResponse>> getMessagesByConversation(@PathVariable Long conversationId) {
        log.info("Getting messages for conversation: {}", conversationId);
        List<MessageResponse> messages = messageService.getMessagesByConversation(conversationId);
        log.info("Found {} messages", messages.size());
        return ApiResponse.<List<MessageResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(messageService.getMessagesByConversation(conversationId))
                .message("Get all messages in conversation")
                .build();
    }
}
