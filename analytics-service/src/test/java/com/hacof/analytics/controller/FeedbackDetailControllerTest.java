package com.hacof.analytics.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
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
import com.hacof.analytics.dto.request.FeedbackDetailRequest;
import com.hacof.analytics.dto.response.FeedbackDetailResponse;
import com.hacof.analytics.service.FeedbackDetailService;

public class FeedbackDetailControllerTest {

    @Mock
    private FeedbackDetailService feedbackDetailService;

    @InjectMocks
    private FeedbackDetailController feedbackDetailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFeedbackDetailsByFeedbackId_validId_returnsData() {
        Long feedbackId = 1L;
        FeedbackDetailResponse detail = new FeedbackDetailResponse();
        detail.setContent("Feedback content example");

        List<FeedbackDetailResponse> mockList = Collections.singletonList(detail);

        when(feedbackDetailService.getFeedbackDetailsByFeedbackId(feedbackId)).thenReturn(mockList);

        ApiResponse<List<FeedbackDetailResponse>> response = feedbackDetailController.getFeedbackDetailsByFeedbackId(feedbackId);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals("Feedback content example", response.getData().get(0).getContent());
        assertEquals("Retrieved feedback details by feedbackId", response.getMessage());

        verify(feedbackDetailService, times(1)).getFeedbackDetailsByFeedbackId(feedbackId);
    }

    @Test
    void createFeedbackDetail_shouldReturnCreatedResponse() {
        FeedbackDetailRequest requestData = new FeedbackDetailRequest();
        FeedbackDetailResponse responseData = new FeedbackDetailResponse();

        ApiRequest<FeedbackDetailRequest> request = ApiRequest.<FeedbackDetailRequest>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(requestData)
                .build();

        when(feedbackDetailService.createFeedbackDetail(requestData)).thenReturn(responseData);

        ResponseEntity<ApiResponse<FeedbackDetailResponse>> response =
                feedbackDetailController.createFeedbackDetail(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody().getData()).isEqualTo(responseData);
        verify(feedbackDetailService).createFeedbackDetail(requestData);
    }

    @Test
    void createBulkFeedbackDetails_shouldReturnCreatedResponse() {
        FeedbackDetailRequest request1 = new FeedbackDetailRequest();
        FeedbackDetailRequest request2 = new FeedbackDetailRequest();
        List<FeedbackDetailRequest> requestList = List.of(request1, request2);
        List<FeedbackDetailResponse> responseList = List.of(new FeedbackDetailResponse(), new FeedbackDetailResponse());

        ApiRequest<List<FeedbackDetailRequest>> request = ApiRequest.<List<FeedbackDetailRequest>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(requestList)
                .build();

        when(feedbackDetailService.createBulkFeedbackDetails(requestList)).thenReturn(responseList);

        ResponseEntity<ApiResponse<List<FeedbackDetailResponse>>> response =
                feedbackDetailController.createBulkFeedbackDetails(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody().getData()).hasSize(2);
        verify(feedbackDetailService).createBulkFeedbackDetails(requestList);
    }

    @Test
    void getFeedbackDetails_shouldReturnList() {
        List<FeedbackDetailResponse> feedbackList = List.of(new FeedbackDetailResponse(), new FeedbackDetailResponse());

        when(feedbackDetailService.getFeedbackDetails()).thenReturn(feedbackList);

        ApiResponse<List<FeedbackDetailResponse>> response = feedbackDetailController.getFeedbackDetails();

        assertThat(response.getData()).hasSize(2);
        verify(feedbackDetailService).getFeedbackDetails();
    }

    @Test
    void getFeedbackDetail_shouldReturnOneItem() {
        Long id = 1L;
        FeedbackDetailResponse responseItem = new FeedbackDetailResponse();

        when(feedbackDetailService.getFeedbackDetail(id)).thenReturn(responseItem);

        ApiResponse<FeedbackDetailResponse> response = feedbackDetailController.getFeedbackDetail(id);

        assertThat(response.getData()).isEqualTo(responseItem);
        verify(feedbackDetailService).getFeedbackDetail(id);
    }

    @Test
    void deleteFeedbackDetail_shouldReturnVoid() {
        Long id = 1L;

        ApiResponse<Void> response = feedbackDetailController.deleteFeedbackDetail(id);

        assertThat(response.getMessage()).isEqualTo("Feedback detail deleted successfully");
        verify(feedbackDetailService).deleteFeedbackDetail(id);
    }
}
