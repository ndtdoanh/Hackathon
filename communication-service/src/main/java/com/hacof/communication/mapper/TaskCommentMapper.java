package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.TaskCommentRequestDTO;
import com.hacof.communication.dto.response.TaskCommentResponseDTO;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskComment;
import org.springframework.stereotype.Component;

@Component
public class TaskCommentMapper {
    // Chuyển từ TaskCommentRequestDTO sang TaskComment entity
    public TaskComment toEntity(TaskCommentRequestDTO requestDTO, Task task) {
        return TaskComment.builder().content(requestDTO.getContent()).task(task).build();
    }
    // Chuyển từ TaskComment entity sang TaskCommentResponseDTO
    public TaskCommentResponseDTO toDto(TaskComment taskComment) {
        return TaskCommentResponseDTO.builder()
                .id(String.valueOf(taskComment.getId())) // Chuyển đổi long -> String
                .taskId(
                        taskComment.getTask() != null
                                ? String.valueOf(taskComment.getTask().getId())
                                : null)
                .taskTitle(taskComment.getTask().getTitle()) // Giả sử Task có trường `title`
                .content(taskComment.getContent())
                .createdDate(taskComment.getCreatedDate())
                .lastModifiedDate(taskComment.getLastModifiedDate())
                .createdBy(
                        taskComment.getCreatedBy() != null
                                ? taskComment.getCreatedBy().getUsername()
                                : null) // Lấy tên người tạo
                .build();
    }
}
