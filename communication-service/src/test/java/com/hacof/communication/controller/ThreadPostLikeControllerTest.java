package com.hacof.communication.controller;

import com.hacof.communication.dto.request.ThreadPostLikeRequestDTO;
import com.hacof.communication.dto.response.ThreadPostLikeResponseDTO;
import com.hacof.communication.service.ThreadPostLikeService;
import com.hacof.communication.util.CommonRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ThreadPostLikeControllerTest {

    @InjectMocks
    private ThreadPostLikeController controller;

    @Mock
    private ThreadPostLikeService service;

    private CommonRequest<ThreadPostLikeRequestDTO> buildRequest() {
        CommonRequest<ThreadPostLikeRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new ThreadPostLikeRequestDTO());
        return request;
    }

    @Test
    void testCreateThreadPostLike_Success() {
        when(service.createThreadPostLike(any())).thenReturn(new ThreadPostLikeResponseDTO());
        var response = controller.createThreadPostLike(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateThreadPostLike_IllegalArgument() {
        when(service.createThreadPostLike(any())).thenThrow(new IllegalArgumentException("Invalid"));
        var response = controller.createThreadPostLike(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testCreateThreadPostLike_Exception() {
        when(service.createThreadPostLike(any())).thenThrow(new RuntimeException("Fail"));
        var response = controller.createThreadPostLike(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllThreadPostLikes_Success() {
        when(service.getAllThreadPostLikes()).thenReturn(List.of(new ThreadPostLikeResponseDTO()));
        var response = controller.getAllThreadPostLikes();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllThreadPostLikes_Exception() {
        when(service.getAllThreadPostLikes()).thenThrow(new RuntimeException("Fail"));
        var response = controller.getAllThreadPostLikes();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetThreadPostLike_Success() {
        when(service.getThreadPostLike(1L)).thenReturn(List.of(new ThreadPostLikeResponseDTO()));
        var response = controller.getThreadPostLike(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetThreadPostLike_Exception() {
        when(service.getThreadPostLike(1L)).thenThrow(new RuntimeException("Error"));
        var response = controller.getThreadPostLike(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllLikesByThreadPost_Success() {
        when(service.getLikesByThreadPostId(99L)).thenReturn(List.of(new ThreadPostLikeResponseDTO()));
        var response = controller.getAllLikesByThreadPost(99L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllLikesByThreadPost_IllegalArgument() {
        when(service.getLikesByThreadPostId(99L)).thenThrow(new IllegalArgumentException("Not found"));
        var response = controller.getAllLikesByThreadPost(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllLikesByThreadPost_Exception() {
        when(service.getLikesByThreadPostId(99L)).thenThrow(new RuntimeException("Boom"));
        var response = controller.getAllLikesByThreadPost(99L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteThreadPostLike_Success() {
        doNothing().when(service).deleteThreadPostLike(9L);
        var response = controller.deleteThreadPostLike(9L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Thread post like deleted successfully!", response.getBody().getMessage());
    }

    @Test
    void testDeleteThreadPostLike_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid")).when(service).deleteThreadPostLike(9L);
        var response = controller.deleteThreadPostLike(9L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testDeleteThreadPostLike_Exception() {
        doThrow(new RuntimeException("Boom")).when(service).deleteThreadPostLike(9L);
        var response = controller.deleteThreadPostLike(9L);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Boom", response.getBody().getMessage());
    }

    @Test
    void testSetCommonResponseFields_NullFieldCoverage() {
        CommonRequest<ThreadPostLikeRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new ThreadPostLikeRequestDTO());

        when(service.createThreadPostLike(any())).thenReturn(new ThreadPostLikeResponseDTO());

        var response = controller.createThreadPostLike(request);
        var body = response.getBody();

        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }
}
