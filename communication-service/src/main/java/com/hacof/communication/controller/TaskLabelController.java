package com.hacof.communication.controller;

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.TaskLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
