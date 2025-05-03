// package com.hacof.identity.controller;
//
// import com.hacof.communication.dto.ApiResponse;
// import com.hacof.communication.dto.response.NotificationResponse;
// import com.hacof.identity.repository.httpclient.NotificationClient;
// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.util.List;
//
// @RestController
// @RequestMapping("/api/v1/notifications")
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class NotificationController {
//    NotificationClient notificationClient;
//
//    @GetMapping
//    public ApiResponse<List<NotificationResponse>> getNotifications(){
//        return notificationClient.getNotifications();
//    }
// }
