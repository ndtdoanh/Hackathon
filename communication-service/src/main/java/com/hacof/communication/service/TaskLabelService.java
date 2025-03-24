package com.hacof.communication.service;

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;

import java.util.List;

public interface TaskLabelService {

    TaskLabelResponseDTO createTaskLabel(TaskLabelRequestDTO taskLabelRequestDTO);

    TaskLabelResponseDTO updateTaskLabel(Long id, TaskLabelRequestDTO taskLabelRequestDTO);

    void deleteTaskLabel(Long id);

}
