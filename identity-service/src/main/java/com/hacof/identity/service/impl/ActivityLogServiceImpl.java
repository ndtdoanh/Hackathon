package com.hacof.identity.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.request.ActivityLogRequest;
import com.hacof.identity.dto.response.ActivityLogResponse;
import com.hacof.identity.entity.ActivityLog;
import com.hacof.identity.entity.User;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.repository.ActivityLogRepository;
import com.hacof.identity.repository.UserRepository;
import com.hacof.identity.service.ActivityLogService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActivityLogServiceImpl implements ActivityLogService {
    ActivityLogRepository activityLogRepository;
    UserRepository userRepository;

    @Override
    public List<ActivityLogResponse> getLogs() {
        return activityLogRepository.findAll().stream()
                .map(ActivityLogResponse::fromEntity)
                .toList();
    }

    @Override
    public ActivityLogResponse getLog(Long id) {
        return activityLogRepository
                .findById(id)
                .map(ActivityLogResponse::fromEntity)
                .orElseThrow(() -> new AppException(ErrorCode.LOG_NOT_FOUND));
    }

    @Override
    public List<ActivityLogResponse> searchLogs(
            String action,
            String target,
            String username,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            String ipAddress,
            Status status) {
        return activityLogRepository.searchLogs(action, target, username, fromDate, toDate, ipAddress, status).stream()
                .map(ActivityLogResponse::fromEntity)
                .toList();
    }

    @Override
    public void logActivity(Long userId, ActivityLogRequest request) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null);

        ActivityLog log = ActivityLog.builder()
                .user(user)
                .action(request.getAction())
                .target(request.getTarget())
                .changedFields(request.getChangedFields())
                .status(request.getStatus())
                .ipAddress(request.getIpAddress())
                .deviceDetails(request.getDeviceDetails())
                .build();

        activityLogRepository.save(log);
    }
}
