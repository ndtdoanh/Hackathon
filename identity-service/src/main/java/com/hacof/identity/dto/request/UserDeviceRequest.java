package com.hacof.identity.dto.request;

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
public class UserDeviceRequest {
    String userId;
    String deviceId;
    LocalDateTime timeFrom;
    LocalDateTime timeTo;
    UserDeviceStatus status;
}
