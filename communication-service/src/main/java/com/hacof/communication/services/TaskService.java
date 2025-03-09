package com.hacof.communication.services;

import java.util.List;

import com.hacof.communication.dto.request.MoveTaskRequest;
import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.responses.CommonResponse;

public interface TaskService {
    CommonResponse<List<TaskResponseDTO>> getAllTasks();

    CommonResponse<TaskResponseDTO> getTaskById(Long id);

    CommonResponse<TaskResponseDTO> createTask(TaskRequestDTO taskRequestDTO);

    CommonResponse<TaskResponseDTO> updateTask(Long id, TaskRequestDTO taskRequestDTO);

    CommonResponse<String> deleteTask(Long id);

    CommonResponse<String> moveTask(Long taskId, MoveTaskRequest moveTaskRequest);
}
