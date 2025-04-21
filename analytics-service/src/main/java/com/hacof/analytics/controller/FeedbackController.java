package com.hacof.analytics.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.analytics.dto.ApiRequest;
import com.hacof.analytics.dto.ApiResponse;
import com.hacof.analytics.dto.request.FeedbackRequest;
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
    //    @PreAuthorize("hasAuthority('CREATE_FEEDBACK')")
    public ResponseEntity<ApiResponse<FeedbackResponse>> createFeedback(
            @RequestBody @Valid ApiRequest<FeedbackRequest> request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FeedbackResponse>builder()
                        .requestId(request.getRequestId())
                        .requestDateTime(request.getRequestDateTime())
                        .channel(request.getChannel())
                        .data(feedbackService.createFeedback(request.getData()))
                        .message("Feedback created successfully")
                        .build());
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> createBulkFeedback(
            @RequestBody @Valid ApiRequest<List<FeedbackRequest>> request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<List<FeedbackResponse>>builder()
                        .requestId(request.getRequestId())
                        .requestDateTime(request.getRequestDateTime())
                        .channel(request.getChannel())
                        .data(feedbackService.createBulkFeedback(request.getData()))
                        .message("Bulk feedback created successfully")
                        .build());
    }

    @GetMapping
    //    @PreAuthorize("hasAuthority('GET_FEEDBACKS')")
    public ApiResponse<List<FeedbackResponse>> getFeedbacks() {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(feedbackService.getFeedbacks())
                .message("Get all feedbacks")
                .build();
    }

    @GetMapping("/{id}")
    //    @PreAuthorize("hasAuthority('GET_FEEDBACK')")
    public ApiResponse<FeedbackResponse> getFeedback(@PathVariable Long id) {
        return ApiResponse.<FeedbackResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(feedbackService.getFeedback(id))
                .message("Get feedback by ID")
                .build();
    }

    @GetMapping("/by-creator/{username}")
    public ApiResponse<List<FeedbackResponse>> getFeedbacksByCreatedByUserName(@PathVariable String username) {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(feedbackService.getFeedbacksByCreatedByUserName(username))
                .message("Get feedbacks by created by username")
                .build();
    }

    @GetMapping("/by-creator/{username}/hackathon/{hackathonId}")
    public ApiResponse<List<FeedbackResponse>> getFeedbacksByCreatedByUserNameAndHackathon(
            @PathVariable String username, @PathVariable Long hackathonId) {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(feedbackService.getFeedbacksByCreatedByUserNameAndHackathon(username, hackathonId))
                .message("Get feedbacks by created by username and hackathon")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_FEEDBACK')")
    public ApiResponse<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Feedback deleted")
                .build();
    }

    //    @GetMapping("/by-team")
    //    //    @PreAuthorize("hasAuthority('GET_FEEDBACKS_BY_TEAM')")
    //    public ApiResponse<List<FeedbackResponse>> getFeedbacksByTeam(@RequestParam Long teamId) {
    //        return ApiResponse.<List<FeedbackResponse>>builder()
    //                .data(feedbackService.getFeedbacksByTeam(teamId))
    //                .message("Get feedbacks by team")
    //                .build();
    //    }

    @GetMapping("/hackathon/{hackathonId}")
    //    @PreAuthorize("hasAuthority('GET_FEEDBACKS_BY_HACKATHON')")
    public ApiResponse<List<FeedbackResponse>> getFeedbacksByHackathon(@PathVariable Long hackathonId) {
        return ApiResponse.<List<FeedbackResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(feedbackService.getFeedbacksByHackathon(hackathonId))
                .message("Get feedbacks by hackathon")
                .build();
    }

    //    @GetMapping("/by-mentor")
    //    //    @PreAuthorize("hasAuthority('GET_FEEDBACKS_BY_MENTOR')")
    //    public ApiResponse<List<FeedbackResponse>> getFeedbacksByMentor(@RequestParam Long mentorId) {
    //        return ApiResponse.<List<FeedbackResponse>>builder()
    //                .data(feedbackService.getFeedbacksByMentor(mentorId))
    //                .message("Get feedbacks by mentor")
    //                .build();
    //    }
}
