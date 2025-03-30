package com.hacof.communication.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.MessageCreateRequest;
import com.hacof.communication.dto.request.MessageUpdateRequest;
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
            @PathVariable Long conversationId, @RequestBody @Valid MessageCreateRequest request) {
        MessageResponse messageResponse = messageService.createMessage(conversationId, request);
        ApiResponse<MessageResponse> response = ApiResponse.<MessageResponse>builder()
                .data(messageResponse)
                .message("Message created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/conversation/{conversationId}")
    //    @PreAuthorize("hasAuthority('GET_MESSAGES')")
    public ApiResponse<List<MessageResponse>> getMessagesByConversation(@PathVariable Long conversationId) {
        return ApiResponse.<List<MessageResponse>>builder()
                .data(messageService.getMessagesByConversation(conversationId))
                .message("Get all messages in conversation")
                .build();
    }

    @PutMapping("/{messageId}")
    //    @PreAuthorize("hasAuthority('UPDATE_MESSAGE')")
    public ApiResponse<MessageResponse> updateMessage(
            @PathVariable Long messageId, @RequestBody MessageUpdateRequest request) {
        return ApiResponse.<MessageResponse>builder()
                .data(messageService.updateMessage(messageId, request))
                .message("Message updated successfully")
                .build();
    }

    @DeleteMapping("/{messageId}")
    //    @PreAuthorize("hasAuthority('DELETE_MESSAGE')")
    public ApiResponse<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ApiResponse.<Void>builder().message("Message has been deleted").build();
    }
}
