package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.TaskDTO;
import com.hacof.hackathon.service.TaskService;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    // @PreAuthorize("hasAuthority('ASSIGN_TASK')")
    public ResponseEntity<CommonResponse<TaskDTO>> assignTask(@RequestBody CommonRequest<TaskDTO> request) {
        TaskDTO taskDTO = taskService.assignTask(request.getData());
        CommonResponse<TaskDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Task assigned successfully"),
                taskDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    //   @PreAuthorize("hasAuthority('GET_TASKS')")
    public ResponseEntity<CommonResponse<List<TaskDTO>>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        CommonResponse<List<TaskDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched all tasks successfully"),
                tasks);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{taskId}")
    //   @PreAuthorize("hasAuthority('GET_TASK')")
    public ResponseEntity<CommonResponse<TaskDTO>> getTaskById(@PathVariable Long taskId) {
        TaskDTO task = taskService.getTaskById(taskId);
        CommonResponse<TaskDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Fetched task successfully"),
                task);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}")
    //  @PreAuthorize("hasAuthority('UPDATE_TASK')")
    public ResponseEntity<CommonResponse<TaskDTO>> updateTask(
            @PathVariable Long taskId, @RequestBody CommonRequest<TaskDTO> request) {
        TaskDTO taskDTO = taskService.updateTask(taskId, request.getData());
        CommonResponse<TaskDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Task updated successfully"),
                taskDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    // @PreAuthorize("hasAuthority('DELETE_TASK')")
    public ResponseEntity<CommonResponse<Void>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        CommonResponse<Void> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result("0000", "Task deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }
}
