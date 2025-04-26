package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class NotificationResponse {
    String id;
    UserResponse sender;
    NotificationType type;
    String content;
    String metadata;
    List<NotificationDeliveryResponse> notificationDeliveries;

    @JsonProperty("isRead")
    boolean isRead;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
