package com.hacof.identity.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hacof.identity.constant.Status;
import com.hacof.identity.dto.ApiResponse;
import com.hacof.identity.dto.response.ActivityLogResponse;
import com.hacof.identity.service.ActivityLogService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class ActivityLogControllerTest {

    @Mock
    ActivityLogService activityLogService;

    @InjectMocks
    ActivityLogController activityLogController;

    @Test
    void testGetLogs() {
        List<ActivityLogResponse> mockLogs = Collections.singletonList(new ActivityLogResponse());
        when(activityLogService.getLogs()).thenReturn(mockLogs);

        ApiResponse<List<ActivityLogResponse>> response = activityLogController.getLogs();

        assertEquals(mockLogs, response.getData());
        assertEquals("Get all logs successfully", response.getMessage());
        verify(activityLogService, times(1)).getLogs();
    }

    @Test
    void testGetLogById() {
        Long id = 1L;
        ActivityLogResponse mockLog = new ActivityLogResponse();
        when(activityLogService.getLog(id)).thenReturn(mockLog);

        ApiResponse<ActivityLogResponse> response = activityLogController.getLog(id);

        assertEquals(mockLog, response.getData());
        assertEquals("Get log by ID successfully", response.getMessage());
        verify(activityLogService, times(1)).getLog(id);
    }

    @Test
    void testSearchLogs() {
        String action = "login";
        String target = "user";
        String username = "user123";
        LocalDateTime fromDate = LocalDateTime.now().minusDays(1);
        LocalDateTime toDate = LocalDateTime.now();
        String ipAddress = "192.168.1.1";
        Status status = Status.SUCCESS;

        List<ActivityLogResponse> mockLogs = Collections.singletonList(new ActivityLogResponse());
        when(activityLogService.searchLogs(action, target, username, fromDate, toDate, ipAddress, status))
                .thenReturn(mockLogs);

        ApiResponse<List<ActivityLogResponse>> response =
                activityLogController.searchLogs(action, target, username, fromDate, toDate, ipAddress, status);

        assertEquals(mockLogs, response.getData());
        assertEquals("Search logs successfully", response.getMessage());
        verify(activityLogService, times(1)).searchLogs(action, target, username, fromDate, toDate, ipAddress, status);
    }
}
