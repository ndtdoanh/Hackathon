package com.hacof.identity.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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
    String userId;
    String deviceId;
    LocalDateTime timeFrom;
    LocalDateTime timeTo;
    UserDeviceStatus status;
    List<FileUrlResponse> fileUrls;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
