package com.hacof.communication.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.hacof.communication.dto.response.FileUrlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hacof.communication.dto.request.BulkTaskUpdateRequestDTO;
import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.service.TaskService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private CommonRequest<TaskRequestDTO> buildRequest() {
        CommonRequest<TaskRequestDTO> request = new CommonRequest<>();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestDateTime(LocalDateTime.now());
        request.setChannel("HACOF");
        request.setData(new TaskRequestDTO());
        return request;
    }

    @Test
    void testSetCommonResponseFields_AllFieldsNull() {
        CommonRequest<TaskRequestDTO> request = new CommonRequest<>();
        request.setRequestId(null);
        request.setRequestDateTime(null);
        request.setChannel(null);
        request.setData(new TaskRequestDTO());

        when(taskService.createTask(any())).thenReturn(new TaskResponseDTO());

        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.createTask(request);
        CommonResponse<TaskResponseDTO> body = response.getBody();

        assertNotNull(body);
        assertNotNull(body.getRequestId());
        assertNotNull(body.getRequestDateTime());
        assertEquals("HACOF", body.getChannel());
    }

    @Test
    void testCreateTask_Success() {
        when(taskService.createTask(any())).thenReturn(new TaskResponseDTO());
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.createTask(buildRequest());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testCreateTask_IllegalArgumentException() {
        when(taskService.createTask(any())).thenThrow(new IllegalArgumentException("Invalid"));
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.createTask(buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateTask_Exception() {
        when(taskService.createTask(any())).thenThrow(new RuntimeException("Error"));
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.createTask(buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskInfo_Success() {
        when(taskService.updateTaskInfo(eq(1L), any())).thenReturn(new TaskResponseDTO());
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.updateTaskInfo(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskInfo_IllegalArgumentException() {
        when(taskService.updateTaskInfo(eq(1L), any())).thenThrow(new IllegalArgumentException("Not Found"));
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.updateTaskInfo(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskInfo_Exception() {
        when(taskService.updateTaskInfo(eq(1L), any())).thenThrow(new RuntimeException("Failed"));
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.updateTaskInfo(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskFiles_Success() {
        when(taskService.updateTaskFiles(eq(1L), any())).thenReturn(new TaskResponseDTO());
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.updateTaskFiles(1L, buildRequest());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskFiles_IllegalArgumentException() {
        when(taskService.updateTaskFiles(eq(1L), any())).thenThrow(new IllegalArgumentException("Invalid"));
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.updateTaskFiles(1L, buildRequest());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTaskFiles_Exception() {
        when(taskService.updateTaskFiles(eq(1L), any())).thenThrow(new RuntimeException("Error"));
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.updateTaskFiles(1L, buildRequest());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTask_Success() {
        doNothing().when(taskService).deleteTask(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTask(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTask_IllegalArgumentException() {
        doThrow(new IllegalArgumentException("Not found")).when(taskService).deleteTask(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTask(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTask_Exception() {
        doThrow(new RuntimeException("Error")).when(taskService).deleteTask(1L);
        ResponseEntity<CommonResponse<String>> response = controller.deleteTask(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetTask_Success() {
        when(taskService.getTask(1L)).thenReturn(new TaskResponseDTO());
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.getTask(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetTask_IllegalArgumentException() {
        when(taskService.getTask(1L)).thenThrow(new IllegalArgumentException("Missing"));
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.getTask(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetTask_Exception() {
        when(taskService.getTask(1L)).thenThrow(new RuntimeException("Oops"));
        ResponseEntity<CommonResponse<TaskResponseDTO>> response = controller.getTask(1L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetAllTasks_Success() {
        when(taskService.getAllTasks()).thenReturn(List.of(new TaskResponseDTO()));
        ResponseEntity<CommonResponse<List<TaskResponseDTO>>> response = controller.getAllTasks();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllTasks_Exception() {
        when(taskService.getAllTasks()).thenThrow(new RuntimeException("DB fail"));
        ResponseEntity<CommonResponse<List<TaskResponseDTO>>> response = controller.getAllTasks();
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBulkTasks_Success() {
        CommonRequest<List<BulkTaskUpdateRequestDTO>> request = new CommonRequest<>();
        request.setData(List.of(new BulkTaskUpdateRequestDTO()));
        when(taskService.updateBulkTasks(any())).thenReturn(List.of(new TaskResponseDTO()));
        ResponseEntity<CommonResponse<List<TaskResponseDTO>>> response = controller.updateBulkTasks(request);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBulkTasks_IllegalArgumentException() {
        CommonRequest<List<BulkTaskUpdateRequestDTO>> request = new CommonRequest<>();
        request.setData(List.of(new BulkTaskUpdateRequestDTO()));
        when(taskService.updateBulkTasks(any())).thenThrow(new IllegalArgumentException("Invalid input"));
        ResponseEntity<CommonResponse<List<TaskResponseDTO>>> response = controller.updateBulkTasks(request);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateBulkTasks_Exception() {
        CommonRequest<List<BulkTaskUpdateRequestDTO>> request = new CommonRequest<>();
        request.setData(List.of(new BulkTaskUpdateRequestDTO()));
        when(taskService.updateBulkTasks(any())).thenThrow(new RuntimeException("Fail"));
        ResponseEntity<CommonResponse<List<TaskResponseDTO>>> response = controller.updateBulkTasks(request);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetTasksByBoardListId_Success() {
        when(taskService.getTasksByBoardListId(9L)).thenReturn(List.of(new TaskResponseDTO()));
        ResponseEntity<CommonResponse<List<TaskResponseDTO>>> response = controller.getTasksByBoardListId(9L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetTasksByBoardListId_IllegalArgumentException() {
        when(taskService.getTasksByBoardListId(9L)).thenThrow(new IllegalArgumentException("Bad"));
        ResponseEntity<CommonResponse<List<TaskResponseDTO>>> response = controller.getTasksByBoardListId(9L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetTasksByBoardListId_Exception() {
        when(taskService.getTasksByBoardListId(9L)).thenThrow(new RuntimeException("Failed"));
        ResponseEntity<CommonResponse<List<TaskResponseDTO>>> response = controller.getTasksByBoardListId(9L);
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testGetFileUrlsByTaskId_Success() {
        List<FileUrlResponse> fileUrls = List.of(new FileUrlResponse());
        when(taskService.getFileUrlsByTaskId(123L)).thenReturn(fileUrls);

        ResponseEntity<CommonResponse<List<FileUrlResponse>>> response = controller.getFileUrlsByTaskId(123L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("File URLs fetched successfully!", response.getBody().getMessage());
        assertEquals(fileUrls, response.getBody().getData());
    }

}
