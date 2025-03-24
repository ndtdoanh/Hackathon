package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.BoardLabel;
import com.hacof.communication.entity.TaskLabel;
import com.hacof.communication.mapper.TaskLabelMapper;
import com.hacof.communication.repository.TaskLabelRepository;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.repository.BoardLabelRepository;
import com.hacof.communication.service.TaskLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskLabelServiceImpl implements TaskLabelService {

    @Autowired
    private TaskLabelRepository taskLabelRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardLabelRepository boardLabelRepository;

    @Autowired
    private TaskLabelMapper taskLabelMapper;

    @Override
    public TaskLabelResponseDTO createTaskLabel(TaskLabelRequestDTO taskLabelRequestDTO) {
        Optional<Task> taskOptional = taskRepository.findById(taskLabelRequestDTO.getTaskId());
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(taskLabelRequestDTO.getBoardLabelId());
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("BoardLabel not found!");
        }

        TaskLabel taskLabel = new TaskLabel();
        taskLabel.setTask(taskOptional.get());
        taskLabel.setBoardLabel(boardLabelOptional.get());

        taskLabel = taskLabelRepository.save(taskLabel);

        return taskLabelMapper.toDto(taskLabel);
    }

    @Override
    public TaskLabelResponseDTO updateTaskLabel(Long id, TaskLabelRequestDTO taskLabelRequestDTO) {
        Optional<TaskLabel> taskLabelOptional = taskLabelRepository.findById(id);
        if (!taskLabelOptional.isPresent()) {
            throw new IllegalArgumentException("TaskLabel not found!");
        }

        Optional<Task> taskOptional = taskRepository.findById(taskLabelRequestDTO.getTaskId());
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(taskLabelRequestDTO.getBoardLabelId());
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("BoardLabel not found!");
        }

        TaskLabel taskLabel = taskLabelOptional.get();
        taskLabel.setTask(taskOptional.get());
        taskLabel.setBoardLabel(boardLabelOptional.get());

        taskLabel = taskLabelRepository.save(taskLabel);

        return taskLabelMapper.toDto(taskLabel);
    }
}
