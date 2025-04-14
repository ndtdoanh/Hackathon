package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.ApiRequest;
import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.BulkUpdateReadStatusRequest;
import com.hacof.communication.dto.request.NotificationRequest;
import com.hacof.communication.dto.response.NotificationResponse;
import com.hacof.communication.service.NotificationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    NotificationService notificationService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_NOTIFICATION')")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(
            @RequestBody @Valid ApiRequest<NotificationRequest> request) {
        NotificationResponse notificationResponse = notificationService.createNotification(request.getData());
        ApiResponse<NotificationResponse> response = ApiResponse.<NotificationResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(notificationResponse)
                .message("Notification created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    //    @PreAuthorize("hasAuthority('GET_NOTIFICATIONS')")
    public ApiResponse<List<NotificationResponse>> getNotifications() {
        return ApiResponse.<List<NotificationResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(notificationService.getNotifications())
                .message("Fetched all notifications")
                .build();
    }

    @GetMapping("/{id}")
    //    @PreAuthorize("hasAuthority('GET_NOTIFICATION')")
    public ApiResponse<NotificationResponse> getNotification(@PathVariable("id") Long id) {
        return ApiResponse.<NotificationResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(notificationService.getNotification(id))
                .message("Fetched notification by ID")
                .build();
    }

    @GetMapping("/sender/{senderId}")
    //    @PreAuthorize("hasAuthority('GET_NOTIFICATION')")
    public ApiResponse<List<NotificationResponse>> getNotificationsBySenderId(@PathVariable Long senderId) {
        return ApiResponse.<List<NotificationResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(notificationService.getNotificationsBySenderId(senderId))
                .message("Notifications retrieved successfully")
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<NotificationResponse>> getNotificationsByUserId(@PathVariable Long userId) {
        return ApiResponse.<List<NotificationResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(notificationService.getNotificationsByUserId(userId))
                .message("Notifications for user retrieved successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_NOTIFICATION')")
    public ApiResponse<Void> deleteNotification(@PathVariable("id") Long id) {
        notificationService.deleteNotification(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Notification has been deleted")
                .build();
    }

    @PutMapping("/notification-deliveries/read-status")
    @PreAuthorize("hasAuthority('UPDATE_READ_STATUS')")
    public ApiResponse<String> updateReadStatusBulk(@RequestBody ApiRequest<BulkUpdateReadStatusRequest> request) {
        notificationService.updateReadStatusBulk(request.getData());
        return ApiResponse.<String>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .message("Read status updated successfully")
                .data("OK")
                .build();
    }
}
