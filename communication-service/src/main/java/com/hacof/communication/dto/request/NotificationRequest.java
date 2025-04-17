package com.hacof.communication.dto.request;

import com.hacof.communication.constant.NotificationType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    NotificationType type;
    String content;
    String metadata;
    NotificationDeliveryRequest notificationDeliveryRequest;
}
