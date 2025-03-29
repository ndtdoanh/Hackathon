package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.TaskLabelRequestDTO;
import com.hacof.communication.dto.response.TaskLabelResponseDTO;
import com.hacof.communication.entity.BoardLabel;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskLabel;

@Component
public class TaskLabelMapper {

    // Chuyển từ TaskLabelRequestDTO sang TaskLabel entity
    public TaskLabel toEntity(TaskLabelRequestDTO requestDTO, Task task, BoardLabel boardLabel) {
        return TaskLabel.builder().task(task).boardLabel(boardLabel).build();
    }

    // Chuyển từ TaskLabel entity sang TaskLabelResponseDTO
    public TaskLabelResponseDTO toDto(TaskLabel taskLabel) {
        return TaskLabelResponseDTO.builder()
                .id(String.valueOf(taskLabel.getId())) // Chuyển đổi long -> String
                .taskId(
                        taskLabel.getTask() != null
                                ? String.valueOf(taskLabel.getTask().getId())
                                : null)
                .taskTitle(taskLabel.getTask().getTitle()) // Giả sử Task có trường `title`
                .boardLabelId(
                        taskLabel.getBoardLabel() != null
                                ? String.valueOf(taskLabel.getBoardLabel().getId())
                                : null)
                .boardLabelName(taskLabel.getBoardLabel().getName()) // Giả sử BoardLabel có trường `name`
                .createdDate(taskLabel.getCreatedDate()) // Trường từ AuditBase
                .lastModifiedDate(taskLabel.getLastModifiedDate()) // Trường từ AuditBase
                .build();
    }
}
