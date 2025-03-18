package com.hacof.identity.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.response.ActivityLogResponse;
import com.hacof.identity.service.ActivityLogService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ActivityLogController {
    ActivityLogService activityLogService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_LOGS')")
    public ApiResponse<List<ActivityLogResponse>> getLogs() {
        return ApiResponse.<List<ActivityLogResponse>>builder()
                .result(activityLogService.getLogs())
                .message("Get all logs successfully")
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_LOG')")
    public ApiResponse<ActivityLogResponse> getLog(@PathVariable("id") Long id) {
        return ApiResponse.<ActivityLogResponse>builder()
                .result(activityLogService.getLog(id))
                .message("Get log by ID successfully")
                .build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('SEARCH_LOGS')")
    public ApiResponse<List<ActivityLogResponse>> searchLogs(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String target,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @RequestParam(required = false) String ipAddress,
            @RequestParam(required = false) Status status) {

        return ApiResponse.<List<ActivityLogResponse>>builder()
                .result(activityLogService.searchLogs(action, target, username, fromDate, toDate, ipAddress, status))
                .message("Search logs successfully")
                .build();
    }
}
