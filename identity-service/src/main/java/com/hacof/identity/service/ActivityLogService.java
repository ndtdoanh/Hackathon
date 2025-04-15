package com.hacof.identity.service;

import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.request.ActivityLogRequest;
import com.hacof.identity.dto.response.ActivityLogResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityLogService {

    List<ActivityLogResponse> getLogs();

    ActivityLogResponse getLog(Long id);

    List<ActivityLogResponse> searchLogs(
            String action,
            String target,
            String username,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            String ipAddress,
            Status status);

    void logActivity(Long userId, ActivityLogRequest request);
}
