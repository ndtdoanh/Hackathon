package com.hacof.communication.service;

import com.hacof.communication.dto.request.TaskAssigneeRequestDTO;
import com.hacof.communication.dto.response.TaskAssigneeResponseDTO;

import java.util.List;

public interface TaskAssigneeService {

    TaskAssigneeResponseDTO createTaskAssignee(TaskAssigneeRequestDTO taskAssigneeRequestDTO);

    TaskAssigneeResponseDTO updateTaskAssignee(Long id, TaskAssigneeRequestDTO taskAssigneeRequestDTO);

    void deleteTaskAssignee(Long id);

    TaskAssigneeResponseDTO getTaskAssignee(Long id);

    List<TaskAssigneeResponseDTO> getAllTaskAssignees();

    List<TaskAssigneeResponseDTO> getTaskAssigneesByTaskId(Long taskId);
}
