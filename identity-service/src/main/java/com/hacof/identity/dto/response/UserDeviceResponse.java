package com.hacof.identity.dto.response;

import com.hacof.identity.constant.UserDeviceStatus;
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
