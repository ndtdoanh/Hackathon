package com.hacof.communication.dto.request;

import com.hacof.communication.constant.NotificationMethod;
import com.hacof.communication.constant.NotificationStatus;

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
public class UpdateNotificationRequest {
    String content;
    String metadata;
    NotificationMethod method;
    NotificationStatus status;
}
