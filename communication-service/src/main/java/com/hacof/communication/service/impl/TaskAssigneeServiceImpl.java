package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.TaskAssigneeRequestDTO;
import com.hacof.communication.dto.response.TaskAssigneeResponseDTO;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.User;
import com.hacof.communication.entity.TaskAssignee;
import com.hacof.communication.mapper.TaskAssigneeMapper;
import com.hacof.communication.repository.TaskAssigneeRepository;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.TaskAssigneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<Task> taskOptional = taskRepository.findById(taskAssigneeRequestDTO.getTaskId());
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }
        Optional<User> userOptional = userRepository.findById(taskAssigneeRequestDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found!");
        }

        TaskAssignee taskAssignee = taskAssigneeMapper.toEntity(taskAssigneeRequestDTO, taskOptional.get(), userOptional.get());
        taskAssignee = taskAssigneeRepository.save(taskAssignee);

        return taskAssigneeMapper.toDto(taskAssignee);
    }

    @Override
    public TaskAssigneeResponseDTO updateTaskAssignee(Long id, TaskAssigneeRequestDTO taskAssigneeRequestDTO) {
        Optional<TaskAssignee> taskAssigneeOptional = taskAssigneeRepository.findById(id);
        if (!taskAssigneeOptional.isPresent()) {
            throw new IllegalArgumentException("TaskAssignee not found!");
        }
        Optional<Task> taskOptional = taskRepository.findById(taskAssigneeRequestDTO.getTaskId());
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }
        Optional<User> userOptional = userRepository.findById(taskAssigneeRequestDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found!");
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
            throw new IllegalArgumentException("TaskAssignee not found!");
        }
        taskAssigneeRepository.deleteById(id);
    }

    @Override
    public TaskAssigneeResponseDTO getTaskAssignee(Long id) {
        Optional<TaskAssignee> taskAssigneeOptional = taskAssigneeRepository.findById(id);
        if (!taskAssigneeOptional.isPresent()) {
            throw new IllegalArgumentException("TaskAssignee not found!");
        }
        return taskAssigneeMapper.toDto(taskAssigneeOptional.get());
    }
}
