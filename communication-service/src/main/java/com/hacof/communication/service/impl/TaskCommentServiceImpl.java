package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.TaskCommentRequestDTO;
import com.hacof.communication.dto.response.TaskCommentResponseDTO;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskComment;
import com.hacof.communication.mapper.TaskCommentMapper;
import com.hacof.communication.repository.TaskCommentRepository;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.service.TaskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<Task> taskOptional = taskRepository.findById(taskCommentRequestDTO.getTaskId());
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        TaskComment taskComment = new TaskComment();
        taskComment.setContent(taskCommentRequestDTO.getContent());
        taskComment.setTask(taskOptional.get());
        taskComment = taskCommentRepository.save(taskComment);

        return taskCommentMapper.toDto(taskComment);
    }

    @Override
    public TaskCommentResponseDTO updateTaskComment(Long id, TaskCommentRequestDTO taskCommentRequestDTO) {
        Optional<TaskComment> taskCommentOptional = taskCommentRepository.findById(id);
        if (!taskCommentOptional.isPresent()) {
            throw new IllegalArgumentException("TaskComment not found!");
        }

        Optional<Task> taskOptional = taskRepository.findById(taskCommentRequestDTO.getTaskId());
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        TaskComment taskComment = taskCommentOptional.get();
        taskComment.setContent(taskCommentRequestDTO.getContent());
        taskComment.setTask(taskOptional.get());
        taskComment = taskCommentRepository.save(taskComment);

        return taskCommentMapper.toDto(taskComment);
    }
}
