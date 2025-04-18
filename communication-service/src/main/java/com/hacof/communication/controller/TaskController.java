package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.communication.dto.request.BulkTaskUpdateRequestDTO;
import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.service.TaskService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private void setCommonResponseFields(CommonResponse<?> response, CommonRequest<?> request) {
        response.setRequestId(
                request.getRequestId() != null
                        ? request.getRequestId()
                        : UUID.randomUUID().toString());
        response.setRequestDateTime(
                request.getRequestDateTime() != null ? request.getRequestDateTime() : LocalDateTime.now());
        response.setChannel(request.getChannel() != null ? request.getChannel() : "HACOF");
    }

    private void setDefaultResponseFields(CommonResponse<?> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    @PostMapping
    public ResponseEntity<CommonResponse<TaskResponseDTO>> createTask(
            @RequestBody CommonRequest<TaskRequestDTO> request) {
        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO createdTask = taskService.createTask(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Task created successfully!");
            response.setData(createdTask);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update-info/{id}")
    public ResponseEntity<CommonResponse<TaskResponseDTO>> updateTaskInfo(
            @PathVariable Long id, @RequestBody CommonRequest<TaskRequestDTO> request) {

        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO updatedTask = taskService.updateTaskInfo(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task info updated successfully!");
            response.setData(updatedTask);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update-files/{id}")
    public ResponseEntity<CommonResponse<TaskResponseDTO>> updateTaskFiles(
            @PathVariable Long id, @RequestBody CommonRequest<TaskRequestDTO> request) {

        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO updatedTask = taskService.updateTaskFiles(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task files updated successfully!");
            response.setData(updatedTask);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteTask(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            taskService.deleteTask(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task deleted successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TaskResponseDTO>> getTask(@PathVariable Long id) {
        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO task = taskService.getTask(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task fetched successfully!");
            response.setData(task);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TaskResponseDTO>>> getAllTasks() {
        CommonResponse<List<TaskResponseDTO>> response = new CommonResponse<>();
        try {
            List<TaskResponseDTO> tasks = taskService.getAllTasks();
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Tasks fetched successfully!");
            response.setData(tasks);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/bulk-update")
    public ResponseEntity<CommonResponse<List<TaskResponseDTO>>> updateBulkTasks(
            @RequestBody CommonRequest<List<BulkTaskUpdateRequestDTO>> request) {
        CommonResponse<List<TaskResponseDTO>> response = new CommonResponse<>();
        try {
            List<TaskResponseDTO> updatedTasks = taskService.updateBulkTasks(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Tasks updated successfully!");
            response.setData(updatedTasks);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-board-list/{boardListId}")
    public ResponseEntity<CommonResponse<List<TaskResponseDTO>>> getTasksByBoardListId(@PathVariable Long boardListId) {
        CommonResponse<List<TaskResponseDTO>> response = new CommonResponse<>();
        try {
            List<TaskResponseDTO> tasks = taskService.getTasksByBoardListId(boardListId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Tasks fetched successfully for BoardList ID: " + boardListId);
            response.setData(tasks);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
