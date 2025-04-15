package com.hacof.communication.dto.response;

import com.hacof.communication.constant.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
    List<NotificationDeliveryResponse> notificationDeliveries;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
