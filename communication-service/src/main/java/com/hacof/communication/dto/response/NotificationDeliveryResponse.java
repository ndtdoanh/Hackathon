package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import com.hacof.communication.constant.NotificationMethod;
import com.hacof.communication.constant.NotificationStatus;
import com.hacof.communication.constant.RoleType;

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
public class NotificationDeliveryResponse {
    String id;
    NotificationResponse notification;
    Set<UserResponse> recipients;
    RoleType role;
    NotificationMethod method;
    NotificationStatus status;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
