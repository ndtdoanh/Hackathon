package com.hacof.communication.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.communication.constant.NotificationStatus;
import com.hacof.communication.dto.ApiResponse;
import com.hacof.communication.dto.request.NotificationDeliveryRequest;
import com.hacof.communication.dto.response.NotificationDeliveryResponse;
import com.hacof.communication.service.NotificationDeliveryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/notification-deliveries")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationDeliveryController {
    NotificationDeliveryService notificationDeliveryService;

    @PostMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<List<NotificationDeliveryResponse>>> createNotificationDelivery(
            @PathVariable Long notificationId, @RequestBody @Valid NotificationDeliveryRequest request) {

        List<NotificationDeliveryResponse> deliveryResponses =
                notificationDeliveryService.createNotificationDelivery(notificationId, request);

        ApiResponse<List<NotificationDeliveryResponse>> response =
                ApiResponse.<List<NotificationDeliveryResponse>>builder()
                        .result(deliveryResponses)
                        .message("NotificationDeliveries created successfully")
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/status")
    public ApiResponse<NotificationDeliveryResponse> updateNotificationDeliveryStatus(
            @PathVariable("id") Long id, @RequestParam NotificationStatus status) {
        return ApiResponse.<NotificationDeliveryResponse>builder()
                .result(notificationDeliveryService.updateNotificationDeliveryStatus(id, status))
                .message("NotificationDelivery status updated successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<NotificationDeliveryResponse>> getDeliveriesByNotification(
            @RequestParam("notificationId") Long notificationId) {
        return ApiResponse.<List<NotificationDeliveryResponse>>builder()
                .result(notificationDeliveryService.getDeliveriesByNotification(notificationId))
                .message("Fetched all NotificationDeliveries for Notification")
                .build();
    }
}
