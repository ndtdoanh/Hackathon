package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.ApiRequest;
import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.ConversationRequest;
import com.hacof.communication.dto.response.ConversationResponse;
import com.hacof.communication.service.ConversationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/conversations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationController {

    ConversationService conversationService;

    @PostMapping("/single")
    //    @PreAuthorize("hasAuthority('CREATE_CONVERSATION')")
    public ApiResponse<ConversationResponse> createSingleConversation(
            @RequestBody @Valid ApiRequest<ConversationRequest> request) {
        return ApiResponse.<ConversationResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(conversationService.createSingleConversation(request.getData()))
                .message("Single conversation created successfully")
                .build();
    }

    //    @PostMapping("/group")
    //    //    @PreAuthorize("hasAuthority('CREATE_CONVERSATION')")
    //    public ApiResponse<ConversationResponse> createGroupConversation(
    //            @RequestBody @Valid ConversationCreateRequest request) {
    //        return ApiResponse.<ConversationResponse>builder()
    //                .data(conversationService.createGroupConversation(request))
    //                .message("Group conversation created successfully")
    //                .build();
    //    }

    @GetMapping("/{id}")
    //    @PreAuthorize("hasAuthority('GET_CONVERSATION')")
    public ApiResponse<ConversationResponse> getConversationById(@PathVariable("id") Long id) {
        return ApiResponse.<ConversationResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(conversationService.getConversationById(id))
                .message("Conversation retrieved successfully")
                .build();
    }

    @GetMapping("/users/{userId}")
    //    @PreAuthorize("hasAuthority('GET_USER_CONVERSATIONS')")
    public ApiResponse<List<ConversationResponse>> getConversationsByUserId(@PathVariable("userId") Long userId) {
        return ApiResponse.<List<ConversationResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(conversationService.getConversationsByUserId(userId))
                .message("User conversations retrieved successfully")
                .build();
    }

    //    @PutMapping("/{id}")
    //    //    @PreAuthorize("hasAuthority('UPDATE_CONVERSATION')")
    //    public ApiResponse<ConversationResponse> updateConversation(
    //            @PathVariable("id") Long id, @RequestBody ConversationUpdateRequest request) {
    //        return ApiResponse.<ConversationResponse>builder()
    //                .data(conversationService.updateConversation(id, request))
    //                .message("Conversation updated successfully")
    //                .build();
    //    }

    @DeleteMapping("/{id}")
    //    @PreAuthorize("hasAuthority('DELETE_CONVERSATION')")
    public ApiResponse<Void> deleteConversation(@PathVariable("id") Long id) {
        conversationService.deleteConversation(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Conversation deleted successfully")
                .build();
    }

    //    @PostMapping("/{conversationId}/users/{userId}")
    //    //    @PreAuthorize("hasAuthority('ADD_USER_TO_CONVERSATION')")
    //    public ApiResponse<Void> addUserToConversation(
    //            @PathVariable("conversationId") Long conversationId, @PathVariable("userId") Long userId) {
    //        conversationService.addUserToConversation(conversationId, userId);
    //        return ApiResponse.<Void>builder()
    //                .message("User added to conversation successfully")
    //                .build();
    //    }
    //
    //    @DeleteMapping("/{conversationId}/users/{userId}")
    //    //    @PreAuthorize("hasAuthority('REMOVE_USER_FROM_CONVERSATION')")
    //    public ApiResponse<Void> removeUserFromConversation(
    //            @PathVariable("conversationId") Long conversationId, @PathVariable("userId") Long userId) {
    //        conversationService.removeUserFromConversation(conversationId, userId);
    //        return ApiResponse.<Void>builder()
    //                .message("User removed from conversation successfully")
    //                .build();
    //    }
}
