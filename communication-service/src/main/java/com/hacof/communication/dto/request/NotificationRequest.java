package com.hacof.communication.dto.request;

import com.hacof.communication.constant.NotificationType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    NotificationType notificationType;
    String content;
    String metadata;
    NotificationDeliveryRequest notificationDeliveryRequest;
}
