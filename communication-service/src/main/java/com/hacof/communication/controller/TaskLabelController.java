package com.hacof.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.TaskLabelService;

@RestController
@RequestMapping("/api/v1/task-labels")
public class TaskLabelController {

    @Autowired
    private TaskLabelService taskLabelService;

    @PostMapping
    public ResponseEntity<CommonResponse<TaskLabelResponseDTO>> createTaskLabel(
            @RequestBody TaskLabelRequestDTO taskLabelRequestDTO) {
        CommonResponse<TaskLabelResponseDTO> response = new CommonResponse<>();
        try {
            TaskLabelResponseDTO createdTaskLabel = taskLabelService.createTaskLabel(taskLabelRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Task Label created successfully!");
            response.setData(createdTaskLabel);
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
    public ResponseEntity<CommonResponse<TaskLabelResponseDTO>> updateTaskLabel(
            @PathVariable Long id, @RequestBody TaskLabelRequestDTO taskLabelRequestDTO) {
        CommonResponse<TaskLabelResponseDTO> response = new CommonResponse<>();
        try {
            TaskLabelResponseDTO updatedTaskLabel = taskLabelService.updateTaskLabel(id, taskLabelRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Label updated successfully!");
            response.setData(updatedTaskLabel);
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
    public ResponseEntity<CommonResponse<String>> deleteTaskLabel(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            taskLabelService.deleteTaskLabel(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Task Label deleted successfully!");
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

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TaskLabelResponseDTO>> getTaskLabel(@PathVariable Long id) {
        CommonResponse<TaskLabelResponseDTO> response = new CommonResponse<>();
        try {
            TaskLabelResponseDTO taskLabel = taskLabelService.getTaskLabel(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Label fetched successfully!");
            response.setData(taskLabel);
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

    @GetMapping
    public ResponseEntity<CommonResponse<List<TaskLabelResponseDTO>>> getAllTaskLabels() {
        CommonResponse<List<TaskLabelResponseDTO>> response = new CommonResponse<>();
        try {
            List<TaskLabelResponseDTO> taskLabels = taskLabelService.getAllTaskLabels();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Labels fetched successfully!");
            response.setData(taskLabels);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
