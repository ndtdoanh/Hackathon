package com.hacof.identity.dto.response;

import java.time.LocalDateTime;

import com.hacof.identity.constant.DeviceStatus;

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
public class DeviceResponse {
    String id;
    String name;
    String description;
    DeviceStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
