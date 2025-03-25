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
import com.hacof.communication.dto.request.MessageReactionRequest;
import com.hacof.communication.dto.response.MessageReactionResponse;
import com.hacof.communication.service.MessageReactionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/reactions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageReactionController {
    MessageReactionService reactionService;

    @PostMapping("/{messageId}")
    //    @PreAuthorize("hasAuthority('REACT_TO_MESSAGE')")
    public ResponseEntity<ApiResponse<MessageReactionResponse>> reactToMessage(
            @PathVariable Long messageId, @RequestBody @Valid MessageReactionRequest request) {
        MessageReactionResponse reactionResponse = reactionService.reactToMessage(messageId, request);
        ApiResponse<MessageReactionResponse> response = ApiResponse.<MessageReactionResponse>builder()
                .result(reactionResponse)
                .message("Reaction added successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{reactionId}")
    //    @PreAuthorize("hasAuthority('DELETE_REACTION')")
    public ApiResponse<Void> removeReaction(@PathVariable Long reactionId) {
        reactionService.removeReaction(reactionId);
        return ApiResponse.<Void>builder().message("Reaction has been removed").build();
    }

    @GetMapping("/message/{messageId}")
    //    @PreAuthorize("hasAuthority('GET_REACTIONS')")
    public ApiResponse<List<MessageReactionResponse>> getReactionsByMessage(@PathVariable Long messageId) {
        return ApiResponse.<List<MessageReactionResponse>>builder()
                .result(reactionService.getReactionsByMessage(messageId))
                .message("Get all reactions for message")
                .build();
    }
}
