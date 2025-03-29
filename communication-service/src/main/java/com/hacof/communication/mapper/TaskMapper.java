package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.TaskRequestDTO;
import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.entity.Task;

@Component
public class TaskMapper {

    // Chuyển từ TaskRequestDTO sang Task entity
    public Task toEntity(TaskRequestDTO requestDTO, BoardList boardList) {
        return Task.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .position(requestDTO.getPosition())
                .dueDate(requestDTO.getDueDate())
                .boardList(boardList)
                .build();
    }

    // Chuyển từ Task entity sang TaskResponseDTO
    public TaskResponseDTO toDto(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .position(task.getPosition())
                .dueDate(task.getDueDate())
                .boardListName(task.getBoardList() != null ? task.getBoardList().getName() : null)
                .createdBy(task.getCreatedBy() != null ? task.getCreatedBy().getUsername() : null)
                .createdDate(task.getCreatedDate())
                .lastModifiedDate(task.getLastModifiedDate())
                .build();
    }
}
