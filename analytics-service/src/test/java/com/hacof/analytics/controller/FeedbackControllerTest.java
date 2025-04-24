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
    void testGetFeedbacksByMentor() {
        Long mentorId = 1L;
        List<FeedbackResponse> mockFeedbacks = Collections.singletonList(new FeedbackResponse());
        when(feedbackService.getFeedbacksByMentor(mentorId)).thenReturn(mockFeedbacks);

        ApiResponse<List<FeedbackResponse>> response = feedbackController.getFeedbacksByMentor(mentorId);

        assertEquals(mockFeedbacks, response.getData());
        assertEquals("Get feedbacks by mentor", response.getMessage());
        verify(feedbackService, times(1)).getFeedbacksByMentor(mentorId);
    }

    @Test
    void testGetFeedback() {
        String hackathonId = "1";
        String mentorId = "1";
        FeedbackResponse mockFeedback = new FeedbackResponse();
        when(feedbackService.getFeedback(hackathonId, mentorId)).thenReturn(mockFeedback);

        ApiResponse<FeedbackResponse> response = feedbackController.getFeedback(hackathonId, mentorId);

        assertEquals(mockFeedback, response.getData());
        assertEquals("Feedback retrieved successfully", response.getMessage());
        verify(feedbackService, times(1)).getFeedback(hackathonId, mentorId);
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

    @Test
    void testCreateBulkFeedback() {
        List<FeedbackRequest> feedbackRequests = Collections.singletonList(new FeedbackRequest());
        List<FeedbackResponse> feedbackResponses = Collections.singletonList(new FeedbackResponse());

        ApiRequest<List<FeedbackRequest>> apiRequest = new ApiRequest<>();
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");
        apiRequest.setData(feedbackRequests);

        when(feedbackService.createBulkFeedback(feedbackRequests)).thenReturn(feedbackResponses);

        ResponseEntity<ApiResponse<List<FeedbackResponse>>> response =
                feedbackController.createBulkFeedback(apiRequest);

        assertEquals(feedbackResponses, response.getBody().getData());
        assertEquals("Bulk feedback created successfully", response.getBody().getMessage());
        verify(feedbackService, times(1)).createBulkFeedback(feedbackRequests);
    }

    @Test
    void testGetFeedbacksByCreatedByUserName() {
        String username = "testUser";
        List<FeedbackResponse> mockFeedbacks = Collections.singletonList(new FeedbackResponse());
        when(feedbackService.getFeedbacksByCreatedByUserName(username)).thenReturn(mockFeedbacks);

        ApiResponse<List<FeedbackResponse>> response = feedbackController.getFeedbacksByCreatedByUserName(username);

        assertEquals(mockFeedbacks, response.getData());
        assertEquals("Get feedbacks by created by username", response.getMessage());
        verify(feedbackService, times(1)).getFeedbacksByCreatedByUserName(username);
    }

    @Test
    void testGetFeedbacksByCreatedByUserNameAndHackathon() {
        String username = "testUser";
        Long hackathonId = 1L;
        List<FeedbackResponse> mockFeedbacks = Collections.singletonList(new FeedbackResponse());
        when(feedbackService.getFeedbacksByCreatedByUserNameAndHackathon(username, hackathonId))
                .thenReturn(mockFeedbacks);

        ApiResponse<List<FeedbackResponse>> response =
                feedbackController.getFeedbacksByCreatedByUserNameAndHackathon(username, hackathonId);

        assertEquals(mockFeedbacks, response.getData());
        assertEquals("Get feedbacks by created by username and hackathon", response.getMessage());
        verify(feedbackService, times(1)).getFeedbacksByCreatedByUserNameAndHackathon(username, hackathonId);
    }

    @Test
    void testGetFeedbacksByHackathon() {
        Long hackathonId = 1L;
        List<FeedbackResponse> mockFeedbacks = Collections.singletonList(new FeedbackResponse());
        when(feedbackService.getFeedbacksByHackathon(hackathonId)).thenReturn(mockFeedbacks);

        ApiResponse<List<FeedbackResponse>> response = feedbackController.getFeedbacksByHackathon(hackathonId);

        assertEquals(mockFeedbacks, response.getData());
        assertEquals("Get feedbacks by hackathon", response.getMessage());
        verify(feedbackService, times(1)).getFeedbacksByHackathon(hackathonId);
    }
}
