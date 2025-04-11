package com.hacof.analytics.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody @Valid FeedbackDetailRequest request) {

        FeedbackDetailResponse response = feedbackDetailService.createFeedbackDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<FeedbackDetailResponse>builder()
                        .data(response)
                        .message("Feedback Detail created successfully")
                        .build());
    }

    @GetMapping
    //    @PreAuthorize("hasAuthority('GET_FEEDBACK_DETAILS')")
    public ApiResponse<List<FeedbackDetailResponse>> getFeedbackDetails() {
        return ApiResponse.<List<FeedbackDetailResponse>>builder()
                .data(feedbackDetailService.getFeedbackDetails())
                .message("Retrieved all feedback details")
                .build();
    }

    @GetMapping("/{id}")
    //    @PreAuthorize("hasAuthority('GET_FEEDBACK_DETAIL')")
    public ApiResponse<FeedbackDetailResponse> getFeedbackDetail(@PathVariable Long id) {
        return ApiResponse.<FeedbackDetailResponse>builder()
                .data(feedbackDetailService.getFeedbackDetail(id))
                .message("Retrieved feedback detail")
                .build();
    }

    @DeleteMapping("/{id}")
    //    @PreAuthorize("hasAuthority('DELETE_FEEDBACK_DETAIL')")
    public ApiResponse<Void> deleteFeedbackDetail(@PathVariable Long id) {
        feedbackDetailService.deleteFeedbackDetail(id);
        return ApiResponse.<Void>builder()
                .message("Feedback Detail deleted successfully")
                .build();
    }

    //    @GetMapping("/by-feedback")
    //    //    @PreAuthorize("hasAuthority('GET_FEEDBACK_DETAILS_BY_FEEDBACK')")
    //    public ApiResponse<List<FeedbackDetailResponse>> getFeedbackDetailsByFeedbackId(@RequestParam Long feedbackId)
    // {
    //        return ApiResponse.<List<FeedbackDetailResponse>>builder()
    //                .data(feedbackDetailService.getFeedbackDetailsByFeedbackId(feedbackId))
    //                .message("Retrieved feedback details by feedbackId")
    //                .build();
    //    }

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
