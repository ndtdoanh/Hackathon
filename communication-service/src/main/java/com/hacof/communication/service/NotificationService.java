package com.hacof.communication.service;

import com.hacof.communication.dto.request.BulkUpdateReadStatusRequest;
import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    NotificationResponse createNotification(NotificationRequest request);

    List<NotificationResponse> getNotifications();

    NotificationResponse getNotification(Long id);

    List<NotificationResponse> getNotificationsBySenderId(Long senderId);

    List<NotificationResponse> getNotificationsByUserId(Long userId);

    void deleteNotification(Long id);

    void updateReadStatusBulk(BulkUpdateReadStatusRequest request);
}
