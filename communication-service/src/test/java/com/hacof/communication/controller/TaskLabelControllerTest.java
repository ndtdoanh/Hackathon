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

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;
import com.hacof.communication.service.TaskLabelService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

class TaskLabelControllerTest {

    @Mock
    private TaskLabelService taskLabelService;

    @InjectMocks
    private TaskLabelController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<TaskLabelRequestDTO> buildRequest() {
        CommonRequest<TaskLabelRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new TaskLabelRequestDTO());
        return request;
    }

    @Test
    void testSetCommonResponseFields_AllNullFields() {
        CommonRequest<TaskLabelRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new TaskLabelRequestDTO());

        when(taskLabelService.createTaskLabel(any())).thenReturn(new TaskLabelResponseDTO());

        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.createTaskLabel(request);
        CommonResponse<TaskLabelResponseDTO> body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }

    @Test
    void testCreateTaskLabel_Success() {
        when(taskLabelService.createTaskLabel(any())).thenReturn(new TaskLabelResponseDTO());
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.createTaskLabel(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateTaskLabel_IllegalArgumentException() {
        when(taskLabelService.createTaskLabel(any())).thenThrow(new IllegalArgumentException("Invalid"));
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.createTaskLabel(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateTaskLabel_Exception() {
        when(taskLabelService.createTaskLabel(any())).thenThrow(new RuntimeException("Error"));
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.createTaskLabel(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskLabel_Success() {
        when(taskLabelService.updateTaskLabel(eq(1L), any())).thenReturn(new TaskLabelResponseDTO());
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.updateTaskLabel(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskLabel_IllegalArgumentException() {
        when(taskLabelService.updateTaskLabel(eq(1L), any())).thenThrow(new IllegalArgumentException("Not found"));
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.updateTaskLabel(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskLabel_Exception() {
        when(taskLabelService.updateTaskLabel(eq(1L), any())).thenThrow(new RuntimeException("Fail"));
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.updateTaskLabel(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTaskLabel_Success() {
        doNothing().when(taskLabelService).deleteTaskLabel(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskLabel(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTaskLabel_IllegalArgumentException() {
        doThrow(new IllegalArgumentException("Not found"))
                .when(taskLabelService)
                .deleteTaskLabel(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskLabel(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTaskLabel_Exception() {
        doThrow(new RuntimeException("Oops")).when(taskLabelService).deleteTaskLabel(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTaskLabel(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetTaskLabel_Success() {
        when(taskLabelService.getTaskLabel(1L)).thenReturn(new TaskLabelResponseDTO());
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.getTaskLabel(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetTaskLabel_IllegalArgumentException() {
        when(taskLabelService.getTaskLabel(1L)).thenThrow(new IllegalArgumentException("Missing"));
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.getTaskLabel(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetTaskLabel_Exception() {
        when(taskLabelService.getTaskLabel(1L)).thenThrow(new RuntimeException("Error"));
        ResponseEntity<CommonResponse<TaskLabelResponseDTO>> response = controller.getTaskLabel(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllTaskLabels_Success() {
        when(taskLabelService.getAllTaskLabels()).thenReturn(List.of(new TaskLabelResponseDTO()));
        ResponseEntity<CommonResponse<List<TaskLabelResponseDTO>>> response = controller.getAllTaskLabels();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().getData().isEmpty());
    }

    @Test
    void testGetAllTaskLabels_Exception() {
        when(taskLabelService.getAllTaskLabels()).thenThrow(new RuntimeException("Fail"));
        ResponseEntity<CommonResponse<List<TaskLabelResponseDTO>>> response = controller.getAllTaskLabels();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetTaskLabelsByTaskId_Success() {
        List<TaskLabelResponseDTO> mockList = List.of(new TaskLabelResponseDTO());
        when(taskLabelService.getTaskLabelsByTaskId(99L)).thenReturn(mockList);

        ResponseEntity<CommonResponse<List<TaskLabelResponseDTO>>> response = controller.getTaskLabelsByTaskId(99L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockList, response.getBody().getData());
        assertEquals("Task Labels fetched successfully!", response.getBody().getMessage());
    }

    @Test
    void testGetTaskLabelsByTaskId_IllegalArgument() {
        when(taskLabelService.getTaskLabelsByTaskId(99L)).thenThrow(new IllegalArgumentException("Task not found"));

        ResponseEntity<CommonResponse<List<TaskLabelResponseDTO>>> response = controller.getTaskLabelsByTaskId(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Task not found", response.getBody().getMessage());
    }

    @Test
    void testGetTaskLabelsByTaskId_Exception() {
        when(taskLabelService.getTaskLabelsByTaskId(99L)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<CommonResponse<List<TaskLabelResponseDTO>>> response = controller.getTaskLabelsByTaskId(99L);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected error", response.getBody().getMessage());
    }
}
