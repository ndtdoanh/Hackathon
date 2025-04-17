package com.hacof.identity.dto.request;

import java.time.LocalDateTime;

import com.hacof.identity.constant.UserDeviceStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
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
