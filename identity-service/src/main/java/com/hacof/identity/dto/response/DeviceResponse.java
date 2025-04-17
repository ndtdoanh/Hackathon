package com.hacof.identity.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.identity.constant.DeviceStatus;

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
public class DeviceResponse {
    String id;
    String hackathonId;
    String roundId;
    String roundLocationId;
    String name;
    String description;
    int quantity;
    DeviceStatus status;
    List<FileUrlResponse> fileUrls;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;
}
