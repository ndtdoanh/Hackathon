package com.hacof.analytics.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hacof.analytics.dto.ApiRequest;
import com.hacof.analytics.dto.ApiResponse;
import com.hacof.analytics.dto.request.BlogPostRequest;
import com.hacof.analytics.dto.response.BlogPostResponse;
import com.hacof.analytics.service.BlogPostService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BlogPostControllerTest {

    @Mock
    BlogPostService blogPostService;

    @InjectMocks
    BlogPostController blogPostController;

    @Mock
    ObjectMapper objectMapper;

    @Test
    void testCreateBlogPost_whenJsonProcessingExceptionThrown() throws JsonProcessingException {
        BlogPostRequest requestData = new BlogPostRequest();
        ApiRequest<BlogPostRequest> request = new ApiRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(requestData);

        BlogPostResponse responseData = new BlogPostResponse();

        when(blogPostService.createBlogPost(requestData)).thenReturn(responseData);
        doThrow(JsonProcessingException.class).when(objectMapper).writeValueAsString(any());

        ResponseEntity<ApiResponse<BlogPostResponse>> response = blogPostController.createBlogPost(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Blog post created successfully", response.getBody().getMessage());
        assertEquals(responseData, response.getBody().getData());
        assertEquals(request.getRequestId(), response.getBody().getRequestId());
        assertEquals(request.getRequestDateTime(), response.getBody().getRequestDateTime());
        assertEquals(request.getChannel(), response.getBody().getChannel());

        verify(blogPostService, times(1)).createBlogPost(requestData);
        verify(objectMapper, times(1)).writeValueAsString(any());
    }

    @Test
    void testPublishBlogPost() {
        Long id = 1L;
        BlogPostResponse responseData = new BlogPostResponse();
        when(blogPostService.publishBlogPost(id)).thenReturn(responseData);

        var response = blogPostController.publishBlogPost(id);

        assertEquals(responseData, response.getData());
        assertEquals("Blog post published successfully", response.getMessage());
        verify(blogPostService, times(1)).publishBlogPost(id);
    }

    @Test
    void testUnpublishBlogPost() {
        Long id = 1L;
        BlogPostResponse responseData = new BlogPostResponse();
        when(blogPostService.unpublishBlogPost(id)).thenReturn(responseData);

        var response = blogPostController.unpublishBlogPost(id);

        assertEquals(responseData, response.getData());
        assertEquals("Blog post unpublished successfully", response.getMessage());
        verify(blogPostService, times(1)).unpublishBlogPost(id);
    }

    @Test
    void testGetBlogPostBySlug() {
        String slug = "test-slug";
        BlogPostResponse responseData = new BlogPostResponse();
        when(blogPostService.getBlogPostBySlug(slug)).thenReturn(responseData);

        var response = blogPostController.getBlogPostBySlug(slug);

        assertEquals(responseData, response.getData());
        assertEquals("Blog post retrieved by slug", response.getMessage());
        verify(blogPostService, times(1)).getBlogPostBySlug(slug);
    }

    @Test
    void testUpdateBlogPost() {
        Long id = 1L;
        BlogPostRequest requestData = new BlogPostRequest();
        BlogPostResponse responseData = new BlogPostResponse();
        ApiRequest<BlogPostRequest> request = new ApiRequest<>();

        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(requestData);

        when(blogPostService.updateBlogPost(id, requestData)).thenReturn(responseData);

        var response = blogPostController.updateBlogPost(id, request);

        assertEquals(responseData, response.getData());
        assertEquals("Blog post updated successfully", response.getMessage());
        verify(blogPostService, times(1)).updateBlogPost(id, requestData);
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
        assertEquals("Blog post approved successfully", response.getMessage());
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
