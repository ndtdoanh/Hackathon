//package com.hacof.identity.repository.httpclient;
//
//import com.hacof.analytics.dto.ApiResponse;
//import com.hacof.analytics.dto.response.BlogPostResponse;
//import com.hacof.identity.config.AuthenticationRequestInterceptor;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@FeignClient(name = "analytics-service", configuration = {AuthenticationRequestInterceptor.class})
//public interface BlogPostClient {
//    @GetMapping("/api/v1/blog-posts")
//    ApiResponse<List<BlogPostResponse>> getBlogPosts();
//}
