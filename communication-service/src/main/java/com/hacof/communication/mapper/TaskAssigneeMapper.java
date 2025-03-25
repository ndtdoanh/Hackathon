package com.hacof.communication.mapper;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.TaskAssigneeRequestDTO;
import com.hacof.communication.dto.response.TaskAssigneeResponseDTO;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskAssignee;
import com.hacof.communication.entity.User;

@Component
public class TaskAssigneeMapper {

    // Chuyển từ TaskAssigneeRequestDTO sang TaskAssignee entity
    public TaskAssignee toEntity(TaskAssigneeRequestDTO requestDTO, Task task, User user) {
        return TaskAssignee.builder().task(task).user(user).build();
    }

    // Chuyển từ TaskAssignee entity sang TaskAssigneeResponseDTO
    public TaskAssigneeResponseDTO toDto(TaskAssignee taskAssignee) {
        return TaskAssigneeResponseDTO.builder()
                .id(taskAssignee.getId())
                .taskId(taskAssignee.getTask().getId())
                .taskTitle(taskAssignee.getTask().getTitle()) // Giả sử Task có trường `title`
                .userId(taskAssignee.getUser().getId())
                .userUsername(taskAssignee.getUser().getUsername()) // Giả sử User có trường `username`
                .createdDate(taskAssignee.getCreatedDate())
                .lastModifiedDate(taskAssignee.getLastModifiedDate())
                .build();
    }
}
