package com.hacof.communication.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;
import com.hacof.communication.entity.BoardLabel;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskLabel;
import com.hacof.communication.mapper.TaskLabelMapper;
import com.hacof.communication.repository.BoardLabelRepository;
import com.hacof.communication.repository.TaskLabelRepository;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.service.TaskLabelService;

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
        if (taskLabelRequestDTO.getTaskId() == null || taskLabelRequestDTO.getBoardLabelId() == null) {
            throw new IllegalArgumentException("Task ID and Board Label ID are required.");
        }

        Long taskId = Long.parseLong(taskLabelRequestDTO.getTaskId());
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        Long boardLabelId = Long.parseLong(taskLabelRequestDTO.getBoardLabelId());
        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(boardLabelId);
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("BoardLabel not found!");
        }

        Optional<TaskLabel> existingTaskLabel =
                taskLabelRepository.findByTaskAndBoardLabel(taskOptional.get(), boardLabelOptional.get());
        if (existingTaskLabel.isPresent()) {
            throw new IllegalArgumentException("This task already has the specified board label.");
        }

        TaskLabel taskLabel = new TaskLabel();
        taskLabel.setTask(taskOptional.get());
        taskLabel.setBoardLabel(boardLabelOptional.get());
        taskLabel = taskLabelRepository.save(taskLabel);

        return taskLabelMapper.toDto(taskLabel);
    }

    @Override
    public TaskLabelResponseDTO updateTaskLabel(Long id, TaskLabelRequestDTO taskLabelRequestDTO) {
        if (taskLabelRequestDTO.getTaskId() == null || taskLabelRequestDTO.getBoardLabelId() == null) {
            throw new IllegalArgumentException("Task ID and Board Label ID are required.");
        }

        Optional<TaskLabel> taskLabelOptional = taskLabelRepository.findById(id);
        if (!taskLabelOptional.isPresent()) {
            throw new IllegalArgumentException("TaskLabel not found!");
        }

        Long taskId = Long.parseLong(taskLabelRequestDTO.getTaskId());
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        Long boardLabelId = Long.parseLong(taskLabelRequestDTO.getBoardLabelId());
        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(boardLabelId);
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("BoardLabel not found!");
        }

        TaskLabel taskLabel = taskLabelOptional.get();
        if (taskLabel.getTask().equals(taskOptional.get())
                && taskLabel.getBoardLabel().equals(boardLabelOptional.get())) {
            throw new IllegalArgumentException("This task already has the specified board label.");
        }

        taskLabel.setTask(taskOptional.get());
        taskLabel.setBoardLabel(boardLabelOptional.get());

        taskLabel = taskLabelRepository.save(taskLabel);
        return taskLabelMapper.toDto(taskLabel);
    }

    @Override
    public void deleteTaskLabel(Long id) {
        Optional<TaskLabel> taskLabelOptional = taskLabelRepository.findById(id);
        if (!taskLabelOptional.isPresent()) {
            throw new IllegalArgumentException("TaskLabel not found!");
        }
        taskLabelRepository.deleteById(id);
    }

    @Override
    public TaskLabelResponseDTO getTaskLabel(Long id) {
        Optional<TaskLabel> taskLabelOptional = taskLabelRepository.findById(id);
        if (!taskLabelOptional.isPresent()) {
            throw new IllegalArgumentException("TaskLabel not found!");
        }
        return taskLabelMapper.toDto(taskLabelOptional.get());
    }

    @Override
    public List<TaskLabelResponseDTO> getAllTaskLabels() {
        List<TaskLabel> taskLabels = taskLabelRepository.findAll();
        return taskLabels.stream().map(taskLabelMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskLabelResponseDTO> getTaskLabelsByTaskId(Long taskId) {
        List<TaskLabel> taskLabels = taskLabelRepository.findByTaskId(taskId);
        if (taskLabels.isEmpty()) {
            return new ArrayList<>();
        }
        return taskLabels.stream().map(taskLabelMapper::toDto).collect(Collectors.toList());
    }
}
