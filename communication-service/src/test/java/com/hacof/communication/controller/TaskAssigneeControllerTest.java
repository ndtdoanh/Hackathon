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

import com.hacof.communication.dto.request.TaskAssigneeRequestDTO;
import com.hacof.communication.dto.response.TaskAssigneeResponseDTO;
import com.hacof.communication.service.TaskAssigneeService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

class TaskAssigneeControllerTest {

    @Mock
    private TaskAssigneeService service;

    @InjectMocks
    private TaskAssigneeController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<TaskAssigneeRequestDTO> buildRequest() {
        CommonRequest<TaskAssigneeRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new TaskAssigneeRequestDTO());
        return request;
    }

    @Test
    void testSetCommonFields_AllNull() {
        CommonRequest<TaskAssigneeRequestDTO> request = new CommonRequest<>();
        request.setData(new TaskAssigneeRequestDTO());
        when(service.createTaskAssignee(any())).thenReturn(new TaskAssigneeResponseDTO());

        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response = controller.createTaskAssignee(request);
        assertNotNull(response.getBody().getRequestId());
        assertNotNull(response.getBody().getRequestDateTime());
        assertEquals("HACOF", response.getBody().getChannel());
    }

    @Test
    void testCreate_Success() {
        when(service.createTaskAssignee(any())).thenReturn(new TaskAssigneeResponseDTO());
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response =
                controller.createTaskAssignee(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreate_IllegalArgument() {
        when(service.createTaskAssignee(any())).thenThrow(new IllegalArgumentException("Invalid"));
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response =
                controller.createTaskAssignee(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Exception() {
        when(service.createTaskAssignee(any())).thenThrow(new RuntimeException("Fail"));
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response =
                controller.createTaskAssignee(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Success() {
        when(service.updateTaskAssignee(eq(1L), any())).thenReturn(new TaskAssigneeResponseDTO());
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response =
                controller.updateTaskAssignee(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_IllegalArgument() {
        when(service.updateTaskAssignee(eq(1L), any())).thenThrow(new IllegalArgumentException("Not Found"));
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response =
                controller.updateTaskAssignee(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Exception() {
        when(service.updateTaskAssignee(eq(1L), any())).thenThrow(new RuntimeException("Crash"));
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response =
                controller.updateTaskAssignee(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        doNothing().when(service).deleteTaskAssignee(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskAssignee(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDelete_IllegalArgument() {
        doThrow(new IllegalArgumentException("Not Found")).when(service).deleteTaskAssignee(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskAssignee(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Exception() {
        doThrow(new RuntimeException("Fail")).when(service).deleteTaskAssignee(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskAssignee(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Success() {
        when(service.getTaskAssignee(1L)).thenReturn(new TaskAssigneeResponseDTO());
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response = controller.getTaskAssignee(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_IllegalArgument() {
        when(service.getTaskAssignee(1L)).thenThrow(new IllegalArgumentException("Invalid"));
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response = controller.getTaskAssignee(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Exception() {
        when(service.getTaskAssignee(1L)).thenThrow(new RuntimeException("Server error"));
        ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> response = controller.getTaskAssignee(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAll_Success() {
        when(service.getAllTaskAssignees()).thenReturn(List.of(new TaskAssigneeResponseDTO()));
        ResponseEntity<CommonResponse<List<TaskAssigneeResponseDTO>>> response = controller.getAllTaskAssignees();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAll_Exception() {
        when(service.getAllTaskAssignees()).thenThrow(new RuntimeException("Fail"));
        ResponseEntity<CommonResponse<List<TaskAssigneeResponseDTO>>> response = controller.getAllTaskAssignees();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetByTaskId_Success() {
        when(service.getTaskAssigneesByTaskId(88L)).thenReturn(List.of(new TaskAssigneeResponseDTO()));
        ResponseEntity<CommonResponse<List<TaskAssigneeResponseDTO>>> response =
                controller.getTaskAssigneesByTaskId(88L);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetByTaskId_IllegalArgument() {
        when(service.getTaskAssigneesByTaskId(88L)).thenThrow(new IllegalArgumentException("Invalid"));
        ResponseEntity<CommonResponse<List<TaskAssigneeResponseDTO>>> response =
                controller.getTaskAssigneesByTaskId(88L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetByTaskId_Exception() {
        when(service.getTaskAssigneesByTaskId(88L)).thenThrow(new RuntimeException("Crash"));
        ResponseEntity<CommonResponse<List<TaskAssigneeResponseDTO>>> response =
                controller.getTaskAssigneesByTaskId(88L);
        assertEquals(500, response.getStatusCodeValue());
    }
}
