package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.constant.NotificationStatus;
import com.hacof.communication.dto.request.NotificationDeliveryRequest;
import com.hacof.communication.dto.response.NotificationDeliveryResponse;

public interface NotificationDeliveryService {
    List<NotificationDeliveryResponse> createNotificationDelivery(
            Long notificationId, NotificationDeliveryRequest request);

    NotificationDeliveryResponse updateNotificationDeliveryStatus(Long deliveryId, NotificationStatus status);

    List<NotificationDeliveryResponse> getDeliveriesByNotification(Long notificationId);
}
