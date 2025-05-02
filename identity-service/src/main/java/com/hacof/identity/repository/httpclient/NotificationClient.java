// package com.hacof.identity.repository.httpclient;
//
// import com.hacof.communication.dto.ApiResponse;
// import com.hacof.communication.dto.response.NotificationResponse;
// import com.hacof.identity.config.AuthenticationRequestInterceptor;
// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.web.bind.annotation.GetMapping;
//
// import java.util.List;
//
// @FeignClient(name = "communication-service", configuration = {AuthenticationRequestInterceptor.class})
// public interface NotificationClient {
//    @GetMapping("/api/v1/notifications")
//    ApiResponse<List<NotificationResponse>> getNotifications();
// }
