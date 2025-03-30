package com.hacof.analytics.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.analytics.dto.ApiResponse;
import com.hacof.analytics.dto.request.FeedbackCreateRequest;
import com.hacof.analytics.dto.response.FeedbackResponse;
import com.hacof.analytics.service.FeedbackService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackController {
    FeedbackService feedbackService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_FEEDBACK')")
    public ResponseEntity<ApiResponse<FeedbackResponse>> createFeedback(
            @RequestBody @Valid FeedbackCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FeedbackResponse>builder()
                        .data(feedbackService.createFeedback(request))
                        .message("Feedback created successfully")
                        .build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_FEEDBACKS')")
    public ApiResponse<List<FeedbackResponse>> getFeedbacks() {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .data(feedbackService.getFeedbacks())
                .message("Get all feedbacks")
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_FEEDBACK')")
    public ApiResponse<FeedbackResponse> getFeedback(@PathVariable Long id) {
        return ApiResponse.<FeedbackResponse>builder()
                .data(feedbackService.getFeedback(id))
                .message("Get feedback by ID")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_FEEDBACK')")
    public ApiResponse<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ApiResponse.<Void>builder().message("Feedback deleted").build();
    }

    @GetMapping("/by-team")
    @PreAuthorize("hasAuthority('GET_FEEDBACKS_BY_TEAM')")
    public ApiResponse<List<FeedbackResponse>> getFeedbacksByTeam(@RequestParam Long teamId) {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .data(feedbackService.getFeedbacksByTeam(teamId))
                .message("Get feedbacks by team")
                .build();
    }

    @GetMapping("/by-hackathon")
    @PreAuthorize("hasAuthority('GET_FEEDBACKS_BY_HACKATHON')")
    public ApiResponse<List<FeedbackResponse>> getFeedbacksByHackathon(@RequestParam Long hackathonId) {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .data(feedbackService.getFeedbacksByHackathon(hackathonId))
                .message("Get feedbacks by hackathon")
                .build();
    }

    @GetMapping("/by-mentor")
    @PreAuthorize("hasAuthority('GET_FEEDBACKS_BY_MENTOR')")
    public ApiResponse<List<FeedbackResponse>> getFeedbacksByMentor(@RequestParam Long mentorId) {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .data(feedbackService.getFeedbacksByMentor(mentorId))
                .message("Get feedbacks by mentor")
                .build();
    }
}
