package com.hacof.analytics.service;

import java.util.List;

import com.hacof.analytics.dto.request.BlogPostRequest;
import com.hacof.analytics.dto.response.BlogPostResponse;

public interface BlogPostService {

    BlogPostResponse createBlogPost(BlogPostRequest request);

    List<BlogPostResponse> getBlogPosts();

    BlogPostResponse getBlogPost(Long id);

    List<BlogPostResponse> getPublishedBlogPosts();

    BlogPostResponse submitBlogPost(Long id);

    BlogPostResponse approveBlogPost(Long id);

    BlogPostResponse rejectBlogPost(Long id);

    void deleteBlogPost(Long id);
}
