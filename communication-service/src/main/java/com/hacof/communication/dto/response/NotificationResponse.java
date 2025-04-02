package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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
public class NotificationResponse {
    String id;
    UserResponse sender;
    NotificationType type;
    String content;
    String metadata;
    boolean isRead;
    List<NotificationDeliveryResponse> notificationDeliveries;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
