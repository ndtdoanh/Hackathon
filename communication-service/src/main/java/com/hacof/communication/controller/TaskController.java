package com.hacof.communication.controller;

import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<CommonResponse<TaskResponseDTO>> createTask(
            @RequestBody TaskRequestDTO taskRequestDTO) {
        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Task created successfully!");
            response.setData(createdTask);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TaskResponseDTO>> updateTask(
            @PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO) {
        CommonResponse<TaskResponseDTO> response = new CommonResponse<>();
        try {
            TaskResponseDTO updatedTask = taskService.updateTask(id, taskRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task updated successfully!");
            response.setData(updatedTask);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
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
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Task deleted successfully!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
