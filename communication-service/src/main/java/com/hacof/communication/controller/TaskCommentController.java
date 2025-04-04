package com.hacof.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.TaskCommentRequestDTO;
import com.hacof.communication.dto.response.TaskCommentResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.TaskCommentService;

@RestController
@RequestMapping("/api/v1/task-comments")
public class TaskCommentController {

    @Autowired
    private TaskCommentService taskCommentService;

    @PostMapping
    public ResponseEntity<CommonResponse<TaskCommentResponseDTO>> createTaskComment(
            @RequestBody TaskCommentRequestDTO taskCommentRequestDTO) {
        CommonResponse<TaskCommentResponseDTO> response = new CommonResponse<>();
        try {
            TaskCommentResponseDTO createdTaskComment = taskCommentService.createTaskComment(taskCommentRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Task Comment created successfully!");
            response.setData(createdTaskComment);
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
    public ResponseEntity<CommonResponse<TaskCommentResponseDTO>> updateTaskComment(
            @PathVariable Long id, @RequestBody TaskCommentRequestDTO taskCommentRequestDTO) {
        CommonResponse<TaskCommentResponseDTO> response = new CommonResponse<>();
        try {
            TaskCommentResponseDTO updatedTaskComment = taskCommentService.updateTaskComment(id, taskCommentRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Comment updated successfully!");
            response.setData(updatedTaskComment);
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
    public ResponseEntity<CommonResponse<String>> deleteTaskComment(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            taskCommentService.deleteTaskComment(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Task Comment deleted successfully!");
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
    public ResponseEntity<CommonResponse<TaskCommentResponseDTO>> getTaskComment(@PathVariable Long id) {
        CommonResponse<TaskCommentResponseDTO> response = new CommonResponse<>();
        try {
            TaskCommentResponseDTO taskComment = taskCommentService.getTaskComment(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Comment fetched successfully!");
            response.setData(taskComment);
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
    public ResponseEntity<CommonResponse<List<TaskCommentResponseDTO>>> getAllTaskComments() {
        CommonResponse<List<TaskCommentResponseDTO>> response = new CommonResponse<>();
        try {
            List<TaskCommentResponseDTO> taskComments = taskCommentService.getAllTaskComments();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Comments fetched successfully!");
            response.setData(taskComments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
