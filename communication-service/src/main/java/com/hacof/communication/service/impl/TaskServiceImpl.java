package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.entity.Task;
import com.hacof.communication.mapper.TaskMapper;
import com.hacof.communication.repository.BoardListRepository;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardListRepository boardListRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        Long boardListId = Long.parseLong(taskRequestDTO.getBoardListId());
        Optional<BoardList> boardListOptional = boardListRepository.findById(boardListId);

        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList not found!");
        }

        Task task = taskMapper.toEntity(taskRequestDTO, boardListOptional.get());
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        Long boardListId = Long.parseLong(taskRequestDTO.getBoardListId());
        Optional<BoardList> boardListOptional = boardListRepository.findById(boardListId);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList not found!");
        }

        Task task = taskOptional.get();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setPosition(taskRequestDTO.getPosition());
        task.setDueDate(taskRequestDTO.getDueDate());
        task.setBoardList(boardListOptional.get());

        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    public void deleteTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDTO getTask(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            throw new IllegalArgumentException("Task not found!");
        }

        return taskMapper.toDto(taskOptional.get());
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(taskMapper::toDto).collect(Collectors.toList());
    }
}
