package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;
import com.hacof.communication.entity.TaskLabel;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.BoardLabel;
import org.springframework.stereotype.Component;

@Component
public class TaskLabelMapper {

    // Chuyển từ TaskLabelRequestDTO sang TaskLabel entity
    public TaskLabel toEntity(TaskLabelRequestDTO requestDTO, Task task, BoardLabel boardLabel) {
        return TaskLabel.builder()
                .task(task)
                .boardLabel(boardLabel)
                .build();
    }

    // Chuyển từ TaskLabel entity sang TaskLabelResponseDTO
    public TaskLabelResponseDTO toDto(TaskLabel taskLabel) {
        return TaskLabelResponseDTO.builder()
                .id(taskLabel.getId())
                .taskId(taskLabel.getTask().getId())
                .taskTitle(taskLabel.getTask().getTitle())  // Giả sử Task có trường `title`
                .boardLabelId(taskLabel.getBoardLabel().getId())
                .boardLabelName(taskLabel.getBoardLabel().getName()) // Giả sử BoardLabel có trường `name`
                .createdDate(taskLabel.getCreatedDate()) // Trường từ AuditBase
                .lastModifiedDate(taskLabel.getLastModifiedDate()) // Trường từ AuditBase
                .build();
    }
}
