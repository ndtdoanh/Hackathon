package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;

public interface NotificationService {
    NotificationResponse createNotification(NotificationRequest request);

    List<NotificationResponse> getNotifications();

    NotificationResponse getNotification(Long id);

    NotificationResponse updateNotification(Long id, NotificationRequest request);

    void deleteNotification(Long id);
}
