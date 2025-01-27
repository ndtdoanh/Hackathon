package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.TaskDTO;

public interface TaskService {
    TaskDTO assignTask(TaskDTO taskDTO);

    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long id);

    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    void deleteTask(Long id);
}
