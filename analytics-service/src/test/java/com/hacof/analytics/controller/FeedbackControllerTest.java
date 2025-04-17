package com.hacof.analytics.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hacof.analytics.dto.ApiRequest;
import com.hacof.analytics.dto.ApiResponse;
import com.hacof.analytics.dto.request.FeedbackRequest;
import com.hacof.analytics.dto.response.FeedbackResponse;
import com.hacof.analytics.service.FeedbackService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
class FeedbackControllerTest {

    @Mock
    FeedbackService feedbackService;

    @InjectMocks
    FeedbackController feedbackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateFeedback() {
        FeedbackRequest feedbackRequest = new FeedbackRequest();
        FeedbackResponse feedbackResponse = new FeedbackResponse();

        ApiRequest<FeedbackRequest> apiRequest = new ApiRequest<>();
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");
        apiRequest.setData(feedbackRequest);

        when(feedbackService.createFeedback(feedbackRequest)).thenReturn(feedbackResponse);

        ResponseEntity<ApiResponse<FeedbackResponse>> response = feedbackController.createFeedback(apiRequest);

        assertEquals(feedbackResponse, response.getBody().getData());
        assertEquals("Feedback created successfully", response.getBody().getMessage());
        verify(feedbackService, times(1)).createFeedback(feedbackRequest);
    }

    @Test
    void testGetFeedbacks() {
        List<FeedbackResponse> mockFeedbacks = Collections.singletonList(new FeedbackResponse());
        when(feedbackService.getFeedbacks()).thenReturn(mockFeedbacks);

        ApiResponse<List<FeedbackResponse>> response = feedbackController.getFeedbacks();

        assertEquals(mockFeedbacks, response.getData());
        assertEquals("Get all feedbacks", response.getMessage());
        verify(feedbackService, times(1)).getFeedbacks();
    }

    @Test
    void testGetFeedbackById() {
        Long id = 1L;
        FeedbackResponse mockFeedback = new FeedbackResponse();
        when(feedbackService.getFeedback(id)).thenReturn(mockFeedback);

        ApiResponse<FeedbackResponse> response = feedbackController.getFeedback(id);

        assertEquals(mockFeedback, response.getData());
        assertEquals("Get feedback by ID", response.getMessage());
        verify(feedbackService, times(1)).getFeedback(id);
    }

    @Test
    void testDeleteFeedback() {
        Long id = 1L;

        ApiResponse<Void> response = feedbackController.deleteFeedback(id);

        assertEquals("Feedback deleted", response.getMessage());
        verify(feedbackService, times(1)).deleteFeedback(id);
    }
}
