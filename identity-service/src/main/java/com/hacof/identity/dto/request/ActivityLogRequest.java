package com.hacof.identity.dto.request;

import java.util.Map;

import com.hacof.identity.constant.Status;

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
public class ActivityLogRequest {
    String action;
    String target;
    Map<String, Object> changedFields;
    Status status;
    String ipAddress;
    String deviceDetails;
}
