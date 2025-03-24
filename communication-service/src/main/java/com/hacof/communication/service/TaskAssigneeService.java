package com.hacof.communication.service;

import com.hacof.communication.dto.request.TaskAssigneeRequestDTO;
import com.hacof.communication.dto.response.TaskAssigneeResponseDTO;

import java.util.List;

public interface TaskAssigneeService {

    TaskAssigneeResponseDTO createTaskAssignee(TaskAssigneeRequestDTO taskAssigneeRequestDTO);

    TaskAssigneeResponseDTO updateTaskAssignee(Long id, TaskAssigneeRequestDTO taskAssigneeRequestDTO);

}
