package com.hacof.identity.dto.response;

import java.time.LocalDateTime;

import com.hacof.identity.constant.UserDeviceStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDeviceResponse {
    String id;
    long userId;
    long deviceId;
    UserDeviceStatus status;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String createdByUserId;
}
