package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.BulkTaskUpdateRequestDTO;
import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.dto.response.TaskResponseDTO;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);

    //    TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO);

    void deleteTask(Long id);

    TaskResponseDTO getTask(Long id);

    List<TaskResponseDTO> getAllTasks();

    List<TaskResponseDTO> updateBulkTasks(List<BulkTaskUpdateRequestDTO> bulkUpdateRequest);

    List<TaskResponseDTO> getTasksByBoardListId(Long boardListId);

    TaskResponseDTO updateTaskInfo(Long id, TaskRequestDTO taskRequestDTO);

    TaskResponseDTO updateTaskFiles(Long id, TaskRequestDTO taskRequestDTO);

    List<FileUrlResponse> getFileUrlsByTaskId(Long taskId);
}
