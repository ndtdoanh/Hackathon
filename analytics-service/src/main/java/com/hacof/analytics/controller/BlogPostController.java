package com.hacof.analytics.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.analytics.dto.ApiRequest;
import com.hacof.analytics.dto.ApiResponse;
import com.hacof.analytics.dto.request.BlogPostRequest;
import com.hacof.analytics.dto.response.BlogPostResponse;
import com.hacof.analytics.service.BlogPostService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/blog-posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogPostController {
    BlogPostService blogPostService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_BLOG_POST')")
    public ResponseEntity<ApiResponse<BlogPostResponse>> createBlogPost(
            @RequestBody ApiRequest<BlogPostRequest> request) {
        ApiResponse<BlogPostResponse> response = ApiResponse.<BlogPostResponse>builder()
                .requestId(request.getRequestId())
                .requestDateTime(request.getRequestDateTime())
                .channel(request.getChannel())
                .data(blogPostService.createBlogPost(request.getData()))
                .message("Blog post created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    //    @PreAuthorize("hasAuthority('GET_BLOG_POSTS')")
    public ApiResponse<List<BlogPostResponse>> getBlogPosts() {
        return ApiResponse.<List<BlogPostResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(blogPostService.getBlogPosts())
                .message("List of blog posts")
                .build();
    }

    @GetMapping("/{id}")
    //    @PreAuthorize("hasAuthority('GET_BLOG_POST')")
    public ApiResponse<BlogPostResponse> getBlogPost(@PathVariable Long id) {
        return ApiResponse.<BlogPostResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(blogPostService.getBlogPost(id))
                .message("Blog post retrieved")
                .build();
    }

    @GetMapping("/published")
    //    @PreAuthorize("hasAuthority('GET_PUBLISHED_BLOG_POST')")
    public ApiResponse<List<BlogPostResponse>> getPublishedBlogPosts() {
        return ApiResponse.<List<BlogPostResponse>>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(blogPostService.getPublishedBlogPosts())
                .message("List of published blog posts")
                .build();
    }

    @PutMapping("/{id}/submit")
    @PreAuthorize("hasAuthority('SUBMIT_BLOG_POST')")
    public ApiResponse<BlogPostResponse> submitBlogPost(@PathVariable Long id) {
        return ApiResponse.<BlogPostResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(blogPostService.submitBlogPost(id))
                .message("Blog post submitted for review")
                .build();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_BLOG_POST')")
    public ApiResponse<BlogPostResponse> approveBlogPost(@PathVariable Long id) {
        return ApiResponse.<BlogPostResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(blogPostService.approveBlogPost(id))
                .message("Blog post approved and published")
                .build();
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('REJECT_BLOG_POST')")
    public ApiResponse<BlogPostResponse> rejectBlogPost(@PathVariable Long id) {
        return ApiResponse.<BlogPostResponse>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .data(blogPostService.rejectBlogPost(id))
                .message("Blog post rejected")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_BLOG_POST')")
    public ApiResponse<Void> deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteBlogPost(id);
        return ApiResponse.<Void>builder()
                .requestId(UUID.randomUUID().toString())
                .requestDateTime(LocalDateTime.now())
                .channel("HACOF")
                .message("Blog post deleted successfully")
                .build();
    }
}
