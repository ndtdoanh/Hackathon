package com.hacof.communication.controller;

import java.util.List;

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

    @MessageMapping("/chat/{conversationId}")
    public void handleWebSocketMessage(@Payload MessageRequest request, @DestinationVariable Long conversationId) {
        log.info("Received WebSocket message for conversation: {}", conversationId);
        log.info("Message content: {}", request.getContent());
//        log.info("Sender info: {}", request.getCreatedByUserName());

        MessageResponse messageResponse = messageService.createMessage(conversationId, request);
        log.info("Created message response: {}", messageResponse);

        String destination = "/topic/conversations/" + conversationId;
        log.info("Sending to destination: {}", destination);
        messagingTemplate.convertAndSend(destination, messageResponse);
    }

    @PostMapping("/{conversationId}")
    //    @PreAuthorize("hasAuthority('CREATE_MESSAGE')")
    public ResponseEntity<ApiResponse<MessageResponse>> createMessage(
            @PathVariable Long conversationId, @RequestBody @Valid MessageRequest request) {
        MessageResponse messageResponse = messageService.createMessage(conversationId, request);
        ApiResponse<MessageResponse> response = ApiResponse.<MessageResponse>builder()
                .data(messageResponse)
                .message("Message created successfully")
                .build();

        messagingTemplate.convertAndSend("/topic/conversations/" + conversationId, messageResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{messageId}")
    // @PreAuthorize("hasAuthority('GET_MESSAGE')")
    public ApiResponse<MessageResponse> getMessageById(@PathVariable Long messageId) {
        MessageResponse messageResponse = messageService.getMessageById(messageId);
        return ApiResponse.<MessageResponse>builder()
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
                .code(200)
                .data(messageService.getMessagesByConversation(conversationId))
                .message("Get all messages in conversation")
                .build();
    }
}
