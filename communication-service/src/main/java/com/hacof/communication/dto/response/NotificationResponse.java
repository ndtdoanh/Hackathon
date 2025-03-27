package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

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
    Long id;
    UserResponse sender;
    UserResponse recipient;
    NotificationType notificationType;
    String content;
    String metadata;
    boolean isRead;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
