package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.TaskCommentRequestDTO;
import com.hacof.communication.dto.response.TaskCommentResponseDTO;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskComment;
import com.hacof.communication.mapper.TaskCommentMapper;
import com.hacof.communication.repository.TaskCommentRepository;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.service.TaskCommentService;

@Service
public class TaskCommentServiceImpl implements TaskCommentService {

    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskCommentMapper taskCommentMapper;

    @Override
    public TaskCommentResponseDTO createTaskComment(TaskCommentRequestDTO taskCommentRequestDTO) {
        if (taskCommentRequestDTO.getContent() == null
                || taskCommentRequestDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }

        if (taskCommentRequestDTO.getTaskId() == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }

        Long taskId = Long.parseLong(taskCommentRequestDTO.getTaskId());
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found!");
        }

        TaskComment taskComment = new TaskComment();
        taskComment.setContent(taskCommentRequestDTO.getContent());
        taskComment.setTask(taskOptional.get());

        taskComment = taskCommentRepository.save(taskComment);
        return taskCommentMapper.toDto(taskComment);
    }

    @Override
    public TaskCommentResponseDTO updateTaskComment(Long id, TaskCommentRequestDTO taskCommentRequestDTO) {
        if (taskCommentRequestDTO.getContent() == null
                || taskCommentRequestDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }

        if (taskCommentRequestDTO.getTaskId() == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }

        Optional<TaskComment> taskCommentOptional = taskCommentRepository.findById(id);
        if (!taskCommentOptional.isPresent()) {
            throw new IllegalArgumentException("TaskComment with ID " + id + " not found!");
        }

        Long taskId = Long.parseLong(taskCommentRequestDTO.getTaskId());
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found!");
        }

        TaskComment taskComment = taskCommentOptional.get();
        taskComment.setContent(taskCommentRequestDTO.getContent());
        taskComment.setTask(taskOptional.get());

        taskComment = taskCommentRepository.save(taskComment);
        return taskCommentMapper.toDto(taskComment);
    }

    @Override
    public void deleteTaskComment(Long id) {
        Optional<TaskComment> taskCommentOptional = taskCommentRepository.findById(id);
        if (!taskCommentOptional.isPresent()) {
            throw new IllegalArgumentException("TaskComment with ID " + id + " not found!");
        }
        taskCommentRepository.deleteById(id);
    }

    @Override
    public TaskCommentResponseDTO getTaskComment(Long id) {
        Optional<TaskComment> taskCommentOptional = taskCommentRepository.findById(id);
        if (!taskCommentOptional.isPresent()) {
            throw new IllegalArgumentException("TaskComment with ID " + id + " not found!");
        }
        return taskCommentMapper.toDto(taskCommentOptional.get());
    }

    @Override
    public List<TaskCommentResponseDTO> getAllTaskComments() {
        List<TaskComment> taskComments = taskCommentRepository.findAll();
        return taskComments.stream().map(taskCommentMapper::toDto).collect(Collectors.toList());
    }
}
