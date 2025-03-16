package com.hacof.identity.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import com.hacof.identity.constant.Status;
import com.hacof.identity.entity.ActivityLog;

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
public class ActivityLogResponse {
    Long id;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    String action;
    String target;
    Map<String, Object> changedFields;
    Status status;
    String ipAddress;
    String deviceDetails;
    String username;

    public static ActivityLogResponse fromEntity(ActivityLog log) {
        return ActivityLogResponse.builder()
                .id(log.getId())
                .createdDate(log.getCreatedDate())
                .lastModifiedDate(log.getLastModifiedDate())
                .action(log.getAction())
                .target(log.getTarget())
                .changedFields(log.getChangedFields())
                .status(log.getStatus())
                .ipAddress(log.getIpAddress())
                .deviceDetails(log.getDeviceDetails())
                .username(log.getUser() != null ? log.getUser().getUsername() : "Unknown")
                .build();
    }
}
