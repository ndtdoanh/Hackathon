package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;

public interface TaskLabelService {

    TaskLabelResponseDTO createTaskLabel(TaskLabelRequestDTO taskLabelRequestDTO);

    TaskLabelResponseDTO updateTaskLabel(Long id, TaskLabelRequestDTO taskLabelRequestDTO);

    void deleteTaskLabel(Long id);

    TaskLabelResponseDTO getTaskLabel(Long id);

    List<TaskLabelResponseDTO> getAllTaskLabels();

    List<TaskLabelResponseDTO> getTaskLabelsByTaskId(Long taskId);
}
