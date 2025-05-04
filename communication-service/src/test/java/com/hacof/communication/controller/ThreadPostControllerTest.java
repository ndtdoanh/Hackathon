package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hacof.communication.dto.request.ThreadPostRequestDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.service.ThreadPostService;
import com.hacof.communication.util.CommonRequest;

@ExtendWith(MockitoExtension.class)
class ThreadPostControllerTest {

    @InjectMocks
    private ThreadPostController controller;

    @Mock
    private ThreadPostService service;

    private CommonRequest<ThreadPostRequestDTO> buildRequest() {
        CommonRequest<ThreadPostRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ThreadPostRequestDTO());
        return request;
    }

    @Test
    void testCreateThreadPost_Success() {
        when(service.createThreadPost(any())).thenReturn(new ThreadPostResponseDTO());
        var response = controller.createThreadPost(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateThreadPost_IllegalArgument() {
        when(service.createThreadPost(any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.createThreadPost(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testCreateThreadPost_Exception() {
        when(service.createThreadPost(any())).thenThrow(new RuntimeException("Fail"));
        var response = controller.createThreadPost(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Fail", response.getBody().getMessage());
    }

    @Test
    void testGetAllThreadPosts_Success() {
        when(service.getAllThreadPosts()).thenReturn(List.of(new ThreadPostResponseDTO()));
        var response = controller.getAllThreadPosts();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllThreadPosts_Exception() {
        when(service.getAllThreadPosts()).thenThrow(new RuntimeException("Crash"));
        var response = controller.getAllThreadPosts();
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Crash"));
    }

    @Test
    void testGetThreadPostById_Success() {
        when(service.getThreadPost(1L)).thenReturn(new ThreadPostResponseDTO());
        var response = controller.getThreadPostById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetThreadPostById_IllegalArgument() {
        when(service.getThreadPost(1L)).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getThreadPostById(1L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void testGetThreadPostById_Exception() {
        when(service.getThreadPost(1L)).thenThrow(new RuntimeException("Unexpected"));
        var response = controller.getThreadPostById(1L);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected", response.getBody().getMessage());
    }

    @Test
    void testUpdateThreadPost_Success() {
        when(service.updateThreadPost(eq(1L), any())).thenReturn(new ThreadPostResponseDTO());
        var response = controller.updateThreadPost(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateThreadPost_IllegalArgument() {
        when(service.updateThreadPost(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.updateThreadPost(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateThreadPost_Exception() {
        when(service.updateThreadPost(eq(1L), any())).thenThrow(new RuntimeException("Boom"));
        var response = controller.updateThreadPost(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteThreadPost_Success() {
        when(service.deleteThreadPost(1L)).thenReturn(new ThreadPostResponseDTO());
        var response = controller.deleteThreadPost(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Thread post deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteThreadPost_IllegalArgument() {
        when(service.deleteThreadPost(1L)).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.deleteThreadPost(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteThreadPost_Exception() {
        when(service.deleteThreadPost(1L)).thenThrow(new RuntimeException("Error"));
        var response = controller.deleteThreadPost(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetThreadPostsByForumThreadId_Success() {
        when(service.getThreadPostsByForumThreadId(9L)).thenReturn(List.of(new ThreadPostResponseDTO()));
        var response = controller.getThreadPostsByForumThreadId(9L);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetThreadPostsByForumThreadId_IllegalArgument() {
        when(service.getThreadPostsByForumThreadId(9L)).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getThreadPostsByForumThreadId(9L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetThreadPostsByForumThreadId_Exception() {
        when(service.getThreadPostsByForumThreadId(9L)).thenThrow(new RuntimeException("Fail"));
        var response = controller.getThreadPostsByForumThreadId(9L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testSetCommonResponseFields_NullFieldCoverage() {
        CommonRequest<ThreadPostRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new ThreadPostRequestDTO());

        when(service.createThreadPost(any())).thenReturn(new ThreadPostResponseDTO());

        var response = controller.createThreadPost(request);
        var body = response.getBody();

        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }
}
