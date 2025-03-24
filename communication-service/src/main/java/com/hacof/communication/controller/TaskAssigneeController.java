package com.hacof.communication.controller;

import com.hacof.communication.dto.request.TaskAssigneeRequestDTO;
import com.hacof.communication.dto.response.TaskAssigneeResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.TaskAssigneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task-assignees")
public class TaskAssigneeController {

    @Autowired
    private TaskAssigneeService taskAssigneeService;

    @PostMapping
    public ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> createTaskAssignee(
            @RequestBody TaskAssigneeRequestDTO taskAssigneeRequestDTO) {
        CommonResponse<TaskAssigneeResponseDTO> response = new CommonResponse<>();
        try {
            TaskAssigneeResponseDTO createdTaskAssignee = taskAssigneeService.createTaskAssignee(taskAssigneeRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Task Assignee created successfully!");
            response.setData(createdTaskAssignee);
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
    public ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> updateTaskAssignee(
            @PathVariable Long id, @RequestBody TaskAssigneeRequestDTO taskAssigneeRequestDTO) {
        CommonResponse<TaskAssigneeResponseDTO> response = new CommonResponse<>();
        try {
            TaskAssigneeResponseDTO updatedTaskAssignee = taskAssigneeService.updateTaskAssignee(id, taskAssigneeRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Assignee updated successfully!");
            response.setData(updatedTaskAssignee);
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
    public ResponseEntity<CommonResponse<String>> deleteTaskAssignee(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            taskAssigneeService.deleteTaskAssignee(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Task Assignee deleted successfully!");
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
    public ResponseEntity<CommonResponse<TaskAssigneeResponseDTO>> getTaskAssignee(@PathVariable Long id) {
        CommonResponse<TaskAssigneeResponseDTO> response = new CommonResponse<>();
        try {
            TaskAssigneeResponseDTO taskAssignee = taskAssigneeService.getTaskAssignee(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Assignee fetched successfully!");
            response.setData(taskAssignee);
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
    public ResponseEntity<CommonResponse<List<TaskAssigneeResponseDTO>>> getAllTaskAssignees() {
        CommonResponse<List<TaskAssigneeResponseDTO>> response = new CommonResponse<>();
        try {
            List<TaskAssigneeResponseDTO> taskAssignees = taskAssigneeService.getAllTaskAssignees();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Assignees fetched successfully!");
            response.setData(taskAssignees);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-task/{taskId}")
    public ResponseEntity<CommonResponse<List<TaskAssigneeResponseDTO>>> getTaskAssigneesByTaskId(@PathVariable Long taskId) {
        CommonResponse<List<TaskAssigneeResponseDTO>> response = new CommonResponse<>();
        try {
            List<TaskAssigneeResponseDTO> taskAssignees = taskAssigneeService.getTaskAssigneesByTaskId(taskId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Task Assignees by Task fetched successfully!");
            response.setData(taskAssignees);
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
}
