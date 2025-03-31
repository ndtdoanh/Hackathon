package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.request.UpdateNotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;

public interface NotificationService {
    NotificationResponse createNotification(NotificationRequest request);

    List<NotificationResponse> getNotifications();

    NotificationResponse getNotification(Long id);

    List<NotificationResponse> getNotificationsBySenderId(Long senderId);

    NotificationResponse updateNotification(Long id, UpdateNotificationRequest request);

    void deleteNotification(Long id);
}
