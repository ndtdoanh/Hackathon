package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.TaskAssigneeRequestDTO;
import com.hacof.communication.dto.response.TaskAssigneeResponseDTO;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskAssignee;
import com.hacof.communication.entity.User;
import com.hacof.communication.mapper.TaskAssigneeMapper;
import com.hacof.communication.repository.TaskAssigneeRepository;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.TaskAssigneeService;

@Service
public class TaskAssigneeServiceImpl implements TaskAssigneeService {

    @Autowired
    private TaskAssigneeRepository taskAssigneeRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskAssigneeMapper taskAssigneeMapper;

    @Override
    public TaskAssigneeResponseDTO createTaskAssignee(TaskAssigneeRequestDTO taskAssigneeRequestDTO) {
        if (taskAssigneeRequestDTO.getTaskId() == null || taskAssigneeRequestDTO.getUserId() == null) {
            throw new IllegalArgumentException("Task ID and User ID must not be null");
        }

        Long taskId = Long.parseLong(taskAssigneeRequestDTO.getTaskId());
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }

        Long userId = Long.parseLong(taskAssigneeRequestDTO.getUserId());
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        boolean alreadyAssigned = taskAssigneeRepository.existsByTaskAndUser(taskOptional.get(), userOptional.get());
        if (alreadyAssigned) {
            throw new IllegalArgumentException("User is already assigned to this task");
        }

        TaskAssignee taskAssignee = taskAssigneeMapper.toEntity(taskAssigneeRequestDTO, taskOptional.get(), userOptional.get());
        taskAssignee = taskAssigneeRepository.save(taskAssignee);

        return taskAssigneeMapper.toDto(taskAssignee);
    }

    @Override
    public TaskAssigneeResponseDTO updateTaskAssignee(Long id, TaskAssigneeRequestDTO taskAssigneeRequestDTO) {
        if (taskAssigneeRequestDTO.getTaskId() == null || taskAssigneeRequestDTO.getUserId() == null) {
            throw new IllegalArgumentException("Task ID and User ID must not be null");
        }

        Optional<TaskAssignee> taskAssigneeOptional = taskAssigneeRepository.findById(id);
        if (!taskAssigneeOptional.isPresent()) {
            throw new IllegalArgumentException("TaskAssignee with ID " + id + " not found");
        }

        Long taskId = Long.parseLong(taskAssigneeRequestDTO.getTaskId());
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }

        Long userId = Long.parseLong(taskAssigneeRequestDTO.getUserId());
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        Optional<TaskAssignee> existingAssignee = taskAssigneeRepository.findByTaskAndUser(taskOptional.get(), userOptional.get());
        if (existingAssignee.isPresent() && !Long.valueOf(existingAssignee.get().getId()).equals(id)) {
            throw new IllegalArgumentException("User is already assigned to this task.");
        }


        TaskAssignee taskAssignee = taskAssigneeOptional.get();
        taskAssignee.setTask(taskOptional.get());
        taskAssignee.setUser(userOptional.get());

        taskAssignee = taskAssigneeRepository.save(taskAssignee);
        return taskAssigneeMapper.toDto(taskAssignee);
    }

    @Override
    public void deleteTaskAssignee(Long id) {
        Optional<TaskAssignee> taskAssigneeOptional = taskAssigneeRepository.findById(id);
        if (!taskAssigneeOptional.isPresent()) {
            throw new IllegalArgumentException("TaskAssignee with ID " + id + " not found");
        }
        taskAssigneeRepository.deleteById(id);
    }

    @Override
    public TaskAssigneeResponseDTO getTaskAssignee(Long id) {
        Optional<TaskAssignee> taskAssigneeOptional = taskAssigneeRepository.findById(id);
        if (!taskAssigneeOptional.isPresent()) {
            throw new IllegalArgumentException("TaskAssignee with ID " + id + " not found");
        }
        return taskAssigneeMapper.toDto(taskAssigneeOptional.get());
    }

    @Override
    public List<TaskAssigneeResponseDTO> getAllTaskAssignees() {
        List<TaskAssignee> taskAssignees = taskAssigneeRepository.findAll();
        return taskAssignees.stream().map(taskAssigneeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskAssigneeResponseDTO> getTaskAssigneesByTaskId(Long taskId) {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }

        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }

        List<TaskAssignee> taskAssignees = taskAssigneeRepository.findByTaskId(taskId);
        return taskAssignees.stream().map(taskAssigneeMapper::toDto).collect(Collectors.toList());
    }
}
