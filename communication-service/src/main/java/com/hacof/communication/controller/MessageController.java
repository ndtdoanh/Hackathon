package com.hacof.communication.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {
    MessageService messageService;

    @PostMapping("/{conversationId}")
    //    @PreAuthorize("hasAuthority('CREATE_MESSAGE')")
    public ResponseEntity<ApiResponse<MessageResponse>> createMessage(
            @PathVariable Long conversationId, @RequestBody @Valid MessageRequest request) {
        MessageResponse messageResponse = messageService.createMessage(conversationId, request);
        ApiResponse<MessageResponse> response = ApiResponse.<MessageResponse>builder()
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
                .data(messageResponse)
                .message("Message retrieved successfully")
                .build();
    }

    @GetMapping("/conversation/{conversationId}")
    //    @PreAuthorize("hasAuthority('GET_MESSAGES')")
    public ApiResponse<List<MessageResponse>> getMessagesByConversation(@PathVariable Long conversationId) {
        return ApiResponse.<List<MessageResponse>>builder()
                .data(messageService.getMessagesByConversation(conversationId))
                .message("Get all messages in conversation")
                .build();
    }
}
