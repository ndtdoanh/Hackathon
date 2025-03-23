package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.entity.Task;
import com.hacof.communication.mapper.TaskMapper;
import com.hacof.communication.repository.TaskRepository;
import com.hacof.communication.repository.BoardListRepository;
import com.hacof.communication.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<BoardList> boardListOptional = boardListRepository.findById(taskRequestDTO.getBoardListId());
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList not found!");
        }

        Task task = taskMapper.toEntity(taskRequestDTO, boardListOptional.get());
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

}
