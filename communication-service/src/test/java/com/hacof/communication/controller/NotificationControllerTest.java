package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hacof.communication.dto.ApiRequest;
import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.BulkUpdateReadStatusRequest;
import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;
import com.hacof.communication.service.NotificationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@FieldDefaults(level = AccessLevel.PRIVATE)
class NotificationControllerTest {

    @InjectMocks
    NotificationController notificationController;

    @Mock
    NotificationService notificationService;

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleWebSocketNotification_validRequest() {
        Long userId = 1L;
        Long notificationId = 100L;

        NotificationRequest request = new NotificationRequest();
        request.setId(notificationId);
        request.setContent("You have a new message");

        NotificationResponse response = new NotificationResponse();
        response.setId(String.valueOf(notificationId));
        response.setContent("You have a new message");

        when(notificationService.getNotification(notificationId)).thenReturn(response);

        notificationController.handleWebSocketNotification(request, userId);

        String expectedDestination = "/topic/notifications/" + userId;
        verify(messagingTemplate, times(1)).convertAndSend(eq(expectedDestination), eq(response));
        verify(notificationService, times(1)).getNotification(notificationId);
    }

    @Test
    void testCreateNotification() {
        NotificationRequest createRequest = new NotificationRequest();
        ApiRequest<NotificationRequest> apiRequest = new ApiRequest<>();
        apiRequest.setData(createRequest);
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");

        NotificationResponse mockResponse = new NotificationResponse();
        when(notificationService.createNotification(createRequest)).thenReturn(mockResponse);

        ResponseEntity<ApiResponse<NotificationResponse>> responseEntity =
                notificationController.createNotification(apiRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(
                "Notification created successfully", responseEntity.getBody().getMessage());
        verify(notificationService, times(1)).createNotification(createRequest);
    }

    @Test
    void testGetNotifications() {
        List<NotificationResponse> mockList = new ArrayList<>();
        when(notificationService.getNotifications()).thenReturn(mockList);

        var response = notificationController.getNotifications();

        assertNotNull(response);
        assertEquals(mockList, response.getData());
        verify(notificationService, times(1)).getNotifications();
    }

    @Test
    void testGetNotification() {
        Long id = 1L;
        NotificationResponse mockResponse = new NotificationResponse();
        when(notificationService.getNotification(id)).thenReturn(mockResponse);

        var response = notificationController.getNotification(id);

        assertNotNull(response);
        assertEquals(mockResponse, response.getData());
        verify(notificationService, times(1)).getNotification(id);
    }

    @Test
    void testGetNotificationsBySenderId() {
        Long senderId = 1L;
        List<NotificationResponse> mockList = new ArrayList<>();
        when(notificationService.getNotificationsBySenderId(senderId)).thenReturn(mockList);

        var response = notificationController.getNotificationsBySenderId(senderId);

        assertNotNull(response);
        assertEquals(mockList, response.getData());
        verify(notificationService, times(1)).getNotificationsBySenderId(senderId);
    }

    @Test
    void testGetNotificationsByUserId() {
        Long userId = 1L;
        List<NotificationResponse> mockList = new ArrayList<>();
        when(notificationService.getNotificationsByUserId(userId)).thenReturn(mockList);

        var response = notificationController.getNotificationsByUserId(userId);

        assertNotNull(response);
        assertEquals(mockList, response.getData());
        verify(notificationService, times(1)).getNotificationsByUserId(userId);
    }

    @Test
    void testDeleteNotification() {
        Long id = 1L;

        var response = notificationController.deleteNotification(id);

        assertNotNull(response);
        assertEquals("Notification has been deleted", response.getMessage());
        verify(notificationService, times(1)).deleteNotification(id);
    }

    @Test
    void testUpdateReadStatusBulk() {
        BulkUpdateReadStatusRequest updateRequest = new BulkUpdateReadStatusRequest();
        ApiRequest<BulkUpdateReadStatusRequest> apiRequest = new ApiRequest<>();
        apiRequest.setData(updateRequest);
        apiRequest.setRequestId(UUID.randomUUID().toString());
        apiRequest.setRequestDateTime(LocalDateTime.now());
        apiRequest.setChannel("HACOF");

        var response = notificationController.updateReadStatusBulk(apiRequest);

        assertNotNull(response);
        assertEquals("Read status updated successfully", response.getMessage());
        verify(notificationService, times(1)).updateReadStatusBulk(updateRequest);
    }
}
