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

    BlogPostResponse publishBlogPost(Long id);

    BlogPostResponse unpublishBlogPost(Long id);

    BlogPostResponse getBlogPostBySlug(String slug);

    BlogPostResponse rejectBlogPost(Long id);

    BlogPostResponse updateBlogPost(Long id, BlogPostRequest request);

    void deleteBlogPost(Long id);
}
