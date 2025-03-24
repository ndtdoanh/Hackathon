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
}
