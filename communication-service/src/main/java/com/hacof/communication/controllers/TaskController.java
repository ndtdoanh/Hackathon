package com.hacof.communication.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.MoveTaskRequest;
import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.responses.CommonResponse;
import com.hacof.communication.services.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_TASKS')")
    public ResponseEntity<CommonResponse<List<TaskResponseDTO>>> getAllTasks() {
        CommonResponse<List<TaskResponseDTO>> response = new CommonResponse<>();
        try {
            List<TaskResponseDTO> data = taskService.getAllTasks().getData();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all tasks successfully!");
            response.setData(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('GET_TASK')")
    public ResponseEntity<CommonResponse<TaskResponseDTO>> getTaskById(@PathVariable Long id) {
        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO task = taskService.getTaskById(id).getData();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched task successfully!");
            response.setData(task);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Task with id " + id + " not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TASK')")
    public ResponseEntity<CommonResponse<TaskResponseDTO>> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO).getData();
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Task created successfully!");
            response.setData(createdTask);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_TASK')")
    public ResponseEntity<CommonResponse<TaskResponseDTO>> updateTask(
            @PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO) {
        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO updatedTask =
                    taskService.updateTask(id, taskRequestDTO).getData();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task updated successfully!");
            response.setData(updatedTask);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_TASK')")
    public ResponseEntity<CommonResponse<String>> deleteTask(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            CommonResponse<String> serviceResponse = taskService.deleteTask(id);
            response.setStatus(serviceResponse.getStatus());
            response.setMessage(serviceResponse.getMessage());
            response.setData(serviceResponse.getData());
            return ResponseEntity.status(serviceResponse.getStatus()).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Task with id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("update/{taskId}")
    @PreAuthorize("hasAuthority('MOVE_TASK')")
    public ResponseEntity<CommonResponse<String>> moveTask(
            @PathVariable Long taskId, @RequestBody MoveTaskRequest moveTaskRequest) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            CommonResponse<String> serviceResponse = taskService.moveTask(taskId, moveTaskRequest);
            response.setStatus(serviceResponse.getStatus());
            response.setMessage(serviceResponse.getMessage());
            response.setData(serviceResponse.getData());
            return ResponseEntity.status(serviceResponse.getStatus()).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
