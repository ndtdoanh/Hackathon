package com.hacof.analytics.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hacof.analytics.dto.ApiRequest;
import com.hacof.analytics.dto.request.BlogPostRequest;
import com.hacof.analytics.dto.response.BlogPostResponse;
import com.hacof.analytics.service.BlogPostService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
class BlogPostControllerTest {

    @Mock
    BlogPostService blogPostService;

    @InjectMocks
    BlogPostController blogPostController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBlogPost() {
        BlogPostRequest requestData = new BlogPostRequest();
        BlogPostResponse responseData = new BlogPostResponse();
        ApiRequest<BlogPostRequest> request = new ApiRequest<>();

        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(requestData);

        when(blogPostService.createBlogPost(requestData)).thenReturn(responseData);

        var response = blogPostController.createBlogPost(request);

        assertEquals(responseData, response.getBody().getData());
        assertEquals("Blog post created successfully", response.getBody().getMessage());
        verify(blogPostService, times(1)).createBlogPost(requestData);
    }

    @Test
    void testGetBlogPosts() {
        List<BlogPostResponse> mockPosts = Collections.singletonList(new BlogPostResponse());
        when(blogPostService.getBlogPosts()).thenReturn(mockPosts);

        var response = blogPostController.getBlogPosts();

        assertEquals(mockPosts, response.getData());
        assertEquals("List of blog posts", response.getMessage());
        verify(blogPostService, times(1)).getBlogPosts();
    }

    @Test
    void testGetBlogPostById() {
        Long id = 1L;
        BlogPostResponse mockPost = new BlogPostResponse();
        when(blogPostService.getBlogPost(id)).thenReturn(mockPost);

        var response = blogPostController.getBlogPost(id);

        assertEquals(mockPost, response.getData());
        assertEquals("Blog post retrieved", response.getMessage());
        verify(blogPostService, times(1)).getBlogPost(id);
    }

    @Test
    void testGetPublishedBlogPosts() {
        List<BlogPostResponse> mockPosts = Collections.singletonList(new BlogPostResponse());
        when(blogPostService.getPublishedBlogPosts()).thenReturn(mockPosts);

        var response = blogPostController.getPublishedBlogPosts();

        assertEquals(mockPosts, response.getData());
        assertEquals("List of published blog posts", response.getMessage());
        verify(blogPostService, times(1)).getPublishedBlogPosts();
    }

    @Test
    void testSubmitBlogPost() {
        Long id = 1L;
        BlogPostResponse responseData = new BlogPostResponse();
        when(blogPostService.submitBlogPost(id)).thenReturn(responseData);

        var response = blogPostController.submitBlogPost(id);

        assertEquals(responseData, response.getData());
        assertEquals("Blog post submitted for review", response.getMessage());
        verify(blogPostService, times(1)).submitBlogPost(id);
    }

    @Test
    void testApproveBlogPost() {
        Long id = 1L;
        BlogPostResponse responseData = new BlogPostResponse();
        when(blogPostService.approveBlogPost(id)).thenReturn(responseData);

        var response = blogPostController.approveBlogPost(id);

        assertEquals(responseData, response.getData());
        assertEquals("Blog post approved and published", response.getMessage());
        verify(blogPostService, times(1)).approveBlogPost(id);
    }

    @Test
    void testRejectBlogPost() {
        Long id = 1L;
        BlogPostResponse responseData = new BlogPostResponse();
        when(blogPostService.rejectBlogPost(id)).thenReturn(responseData);

        var response = blogPostController.rejectBlogPost(id);

        assertEquals(responseData, response.getData());
        assertEquals("Blog post rejected", response.getMessage());
        verify(blogPostService, times(1)).rejectBlogPost(id);
    }

    @Test
    void testDeleteBlogPost() {
        Long id = 1L;

        var response = blogPostController.deleteBlogPost(id);

        assertEquals("Blog post deleted successfully", response.getMessage());
        verify(blogPostService, times(1)).deleteBlogPost(id);
    }
}
