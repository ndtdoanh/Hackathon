package com.hacof.analytics.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<BlogPostResponse>> createBlogPost(@RequestBody BlogPostRequest request) {
        ApiResponse<BlogPostResponse> response = ApiResponse.<BlogPostResponse>builder()
                .result(blogPostService.createBlogPost(request))
                .message("Blog post created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_BLOG_POSTS')")
    public ApiResponse<List<BlogPostResponse>> getBlogPosts() {
        return ApiResponse.<List<BlogPostResponse>>builder()
                .result(blogPostService.getBlogPosts())
                .message("List of blog posts")
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_BLOG_POST')")
    public ApiResponse<BlogPostResponse> getBlogPost(@PathVariable Long id) {
        return ApiResponse.<BlogPostResponse>builder()
                .result(blogPostService.getBlogPost(id))
                .message("Blog post retrieved")
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_BLOG_POST')")
    public ApiResponse<BlogPostResponse> updateBlogPost(@PathVariable Long id, @RequestBody BlogPostRequest request) {
        return ApiResponse.<BlogPostResponse>builder()
                .result(blogPostService.updateBlogPost(id, request))
                .message("Blog post updated successfully")
                .build();
    }

    @PutMapping("/{id}/submit")
    @PreAuthorize("hasAuthority('SUBMIT_BLOG_POST')")
    public ApiResponse<BlogPostResponse> submitBlogPost(@PathVariable Long id) {
        return ApiResponse.<BlogPostResponse>builder()
                .result(blogPostService.submitBlogPost(id))
                .message("Blog post submitted for review")
                .build();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('APPROVE_BLOG_POST')")
    public ApiResponse<BlogPostResponse> approveBlogPost(@PathVariable Long id) {
        return ApiResponse.<BlogPostResponse>builder()
                .result(blogPostService.approveBlogPost(id))
                .message("Blog post approved and published")
                .build();
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAuthority('REJECT_BLOG_POST')")
    public ApiResponse<BlogPostResponse> rejectBlogPost(@PathVariable Long id) {
        return ApiResponse.<BlogPostResponse>builder()
                .result(blogPostService.rejectBlogPost(id))
                .message("Blog post rejected")
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_BLOG_POST')")
    public ApiResponse<Void> deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteBlogPost(id);
        return ApiResponse.<Void>builder()
                .message("Blog post deleted successfully")
                .build();
    }
}
