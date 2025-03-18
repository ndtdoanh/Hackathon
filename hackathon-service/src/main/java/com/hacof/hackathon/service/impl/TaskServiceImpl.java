package com.hacof.hackathon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.TaskDTO;
import com.hacof.hackathon.entity.Task;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.TaskMapper;
import com.hacof.hackathon.repository.TaskRepository;
import com.hacof.hackathon.service.TaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDTO assignTask(TaskDTO taskDTO) {
        Task task = taskMapper.convertToEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        return taskMapper.convertToDTO(savedTask);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDTO).toList();
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        return taskRepository
                .findById(id)
                .map(taskMapper::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Task existingTask =
                taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        // existingTask.setName(taskDTO.getName());
        existingTask.setDescription(taskDTO.getDescription());
        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.convertToDTO(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}
