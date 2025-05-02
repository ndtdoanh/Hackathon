// package com.hacof.identity.controller;
//
// import com.hacof.analytics.dto.ApiResponse;
// import com.hacof.analytics.dto.response.BlogPostResponse;
// import com.hacof.identity.repository.httpclient.BlogPostClient;
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
// @RequestMapping("/api/v1/blog-posts")
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class BlogPostController {
//    BlogPostClient blogPostClient;
//
//    @GetMapping
//    public ApiResponse<List<BlogPostResponse>> getBlogPosts()    {
//        return blogPostClient.getBlogPosts();
//    }
// }
