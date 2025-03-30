// package com.hacof.identity.dto.response;
//
// import java.time.LocalDateTime;
// import java.util.Map;
//
// import com.hacof.identity.constant.Status;
// import com.hacof.identity.entity.ActivityLog;
//
// import lombok.AccessLevel;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.experimental.FieldDefaults;
//
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
// @FieldDefaults(level = AccessLevel.PRIVATE)
// public class ActivityLogResponse {
//    String id;
//    String action;
//    String target;
//    Map<String, Object> changedFields;
//    Status status;
//    String ipAddress;
//    String deviceDetails;
//    LocalDateTime createdAt;
//    LocalDateTime updatedAt;
//    String createdByUserName;
//
//    public static ActivityLogResponse fromEntity(ActivityLog log) {
//        return ActivityLogResponse.builder()
//                .id(String.valueOf(log.getId()))
//                .createdAt(log.getCreatedDate())
//                .updatedAt(log.getLastModifiedDate())
//                .action(log.getAction())
//                .target(log.getTarget())
//                .changedFields(log.getChangedFields())
//                .status(log.getStatus())
//                .ipAddress(log.getIpAddress())
//                .deviceDetails(log.getDeviceDetails())
//                .createdByUserName(log.getUser() != null ? log.getUser().getUsername() : "Unknown")
//                .build();
//    }
// }
