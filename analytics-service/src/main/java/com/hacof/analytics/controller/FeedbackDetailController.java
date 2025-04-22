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
import com.hacof.analytics.dto.request.FeedbackDetailRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.service.FeedbackDetailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/feedback-details")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedbackDetailController {
    FeedbackDetailService feedbackDetailService;

    @PostMapping
    //    @PreAuthorize("hasAuthority('CREATE_FEEDBACK_DETAIL')")
    public ResponseEntity<ApiResponse<FeedbackDetailResponse>> createFeedbackDetail(
            @RequestBody @Valid ApiRequest<FeedbackDetailRequest> request) {

        FeedbackDetailResponse response = feedbackDetailService.createFeedbackDetail(request.getData());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FeedbackDetailResponse>builder()
                        .requestId(request.getRequestId())
                        .requestDateTime(request.getRequestDateTime())
                        .channel(request.getChannel())
                        .data(response)
                        .message("Feedback detail created successfully")
                        .build());
    }

    @PostMapping("/bulk")
    // @PreAuthorize("hasAuthority('CREATE_FEEDBACK_DETAIL')")
    public ResponseEntity<ApiResponse<List<FeedbackDetailResponse>>> createBulkFeedbackDetails(
            @RequestBody @Valid ApiRequest<List<FeedbackDetailRequest>> request) {

        List<FeedbackDetailResponse> responses = feedbackDetailService.createBulkFeedbackDetails(request.getData());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<List<FeedbackDetailResponse>>builder()
                        .requestId(request.getRequestId())
                        .requestDateTime(request.getRequestDateTime())
                        .channel(request.getChannel())
                        .data(responses)
                        .message("Bulk feedback details created successfully")
                        .build());
    }

    @GetMapping
    //    @PreAuthorize("hasAuthority('GET_FEEDBACK_DETAILS')")
    public ApiResponse<List<FeedbackDetailResponse>> getFeedbackDetails() {
        return ApiResponse.<List<FeedbackDetailResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(feedbackDetailService.getFeedbackDetails())
                .message("Retrieved all feedback details")
                .build();
    }

    @GetMapping("/{id}")
    //    @PreAuthorize("hasAuthority('GET_FEEDBACK_DETAIL')")
    public ApiResponse<FeedbackDetailResponse> getFeedbackDetail(@PathVariable Long id) {
        return ApiResponse.<FeedbackDetailResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(feedbackDetailService.getFeedbackDetail(id))
                .message("Retrieved feedback detail")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_FEEDBACK_DETAIL')")
    public ApiResponse<Void> deleteFeedbackDetail(@PathVariable Long id) {
        feedbackDetailService.deleteFeedbackDetail(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Feedback detail deleted successfully")
                .build();
    }

    @GetMapping("/by-feedback/{feedbackId}")
    // @PreAuthorize("hasAuthority('GET_FEEDBACK_DETAILS_BY_FEEDBACK_ID')")
    public ApiResponse<List<FeedbackDetailResponse>> getFeedbackDetailsByFeedbackId(@PathVariable Long feedbackId) {
        return ApiResponse.<List<FeedbackDetailResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(feedbackDetailService.getFeedbackDetailsByFeedbackId(feedbackId))
                .message("Retrieved feedback details by feedbackId")
                .build();
    }

    //    @GetMapping("/by-mentor")
    //    //    @PreAuthorize("hasAuthority('GET_FEEDBACK_DETAILS_BY_MENTOR')")
    //    public ApiResponse<List<FeedbackDetailResponse>> getFeedbackDetailsByMentorId(@RequestParam Long mentorId) {
    //        return ApiResponse.<List<FeedbackDetailResponse>>builder()
    //                .data(feedbackDetailService.getFeedbackDetailsByMentorId(mentorId))
    //                .message("Retrieved feedback details by mentorId")
    //                .build();
    //    }

    //    @GetMapping("/by-hackathon")
    //    //    @PreAuthorize("hasAuthority('GET_FEEDBACK_DETAILS_BY_HACKATHON')")
    //    public ApiResponse<List<FeedbackDetailResponse>> getFeedbackDetailsByHackathonId(@RequestParam Long
    // hackathonId) {
    //        List<FeedbackDetailResponse> response =
    // feedbackDetailService.getFeedbackDetailsByHackathonId(hackathonId);
    //        return ApiResponse.<List<FeedbackDetailResponse>>builder()
    //                .data(response)
    //                .message("Retrieved feedback details by hackathon")
    //                .build();
    //    }
}
