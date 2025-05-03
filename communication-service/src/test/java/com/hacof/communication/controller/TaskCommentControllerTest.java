package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hacof.communication.dto.request.TaskCommentRequestDTO;
import com.hacof.communication.dto.response.TaskCommentResponseDTO;
import com.hacof.communication.service.TaskCommentService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

class TaskCommentControllerTest {

    @Mock
    private TaskCommentService service;

    @InjectMocks
    private TaskCommentController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<TaskCommentRequestDTO> buildRequest() {
        CommonRequest<TaskCommentRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new TaskCommentRequestDTO());
        return request;
    }

    @Test
    void testSetCommonResponseFields_AllNull() {
        CommonRequest<TaskCommentRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new TaskCommentRequestDTO());

        when(service.createTaskComment(any())).thenReturn(new TaskCommentResponseDTO());

        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response = controller.createTaskComment(request);
        CommonResponse<TaskCommentResponseDTO> body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }

    @Test
    void testCreateTaskComment_Success() {
        when(service.createTaskComment(any())).thenReturn(new TaskCommentResponseDTO());
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response = controller.createTaskComment(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateTaskComment_IllegalArgument() {
        when(service.createTaskComment(any())).thenThrow(new IllegalArgumentException("Invalid"));
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response = controller.createTaskComment(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateTaskComment_Exception() {
        when(service.createTaskComment(any())).thenThrow(new RuntimeException("Error"));
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response = controller.createTaskComment(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskComment_Success() {
        when(service.updateTaskComment(eq(1L), any())).thenReturn(new TaskCommentResponseDTO());
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response =
                controller.updateTaskComment(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskComment_IllegalArgument() {
        when(service.updateTaskComment(eq(1L), any())).thenThrow(new IllegalArgumentException("Invalid"));
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response =
                controller.updateTaskComment(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskComment_Exception() {
        when(service.updateTaskComment(eq(1L), any())).thenThrow(new RuntimeException("Oops"));
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response =
                controller.updateTaskComment(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTaskComment_Success() {
        doNothing().when(service).deleteTaskComment(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskComment(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTaskComment_IllegalArgument() {
        doThrow(new IllegalArgumentException("Invalid")).when(service).deleteTaskComment(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskComment(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTaskComment_Exception() {
        doThrow(new RuntimeException("Fail")).when(service).deleteTaskComment(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskComment(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetTaskComment_Success() {
        when(service.getTaskComment(1L)).thenReturn(new TaskCommentResponseDTO());
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response = controller.getTaskComment(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetTaskComment_IllegalArgument() {
        when(service.getTaskComment(1L)).thenThrow(new IllegalArgumentException("Missing"));
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response = controller.getTaskComment(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetTaskComment_Exception() {
        when(service.getTaskComment(1L)).thenThrow(new RuntimeException("Boom"));
        ResponseEntity<CommonResponse<TaskCommentResponseDTO>> response = controller.getTaskComment(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllTaskComments_Success() {
        when(service.getAllTaskComments()).thenReturn(List.of(new TaskCommentResponseDTO()));
        ResponseEntity<CommonResponse<List<TaskCommentResponseDTO>>> response = controller.getAllTaskComments();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllTaskComments_Exception() {
        when(service.getAllTaskComments()).thenThrow(new RuntimeException("Fail"));
        ResponseEntity<CommonResponse<List<TaskCommentResponseDTO>>> response = controller.getAllTaskComments();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetTaskCommentsByTaskId_Success() {
        List<TaskCommentResponseDTO> mockList = List.of(new TaskCommentResponseDTO());
        when(service.getTaskCommentsByTaskId("123")).thenReturn(mockList);

        ResponseEntity<CommonResponse<List<TaskCommentResponseDTO>>> response = controller.getTaskCommentsByTaskId("123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockList, response.getBody().getData());
        assertEquals("Task Comments fetched successfully!", response.getBody().getMessage());
    }

    @Test
    void testGetTaskCommentsByTaskId_Exception() {
        when(service.getTaskCommentsByTaskId("123")).thenThrow(new RuntimeException("Failed to fetch"));

        ResponseEntity<CommonResponse<List<TaskCommentResponseDTO>>> response = controller.getTaskCommentsByTaskId("123");

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Failed to fetch"));
        assertTrue(response.getBody().getData().isEmpty());
    }

}
