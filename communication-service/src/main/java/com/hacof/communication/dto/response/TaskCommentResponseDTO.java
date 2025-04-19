package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCommentResponseDTO {

    String id; // ID của TaskComment
    String taskId; // ID của Task mà TaskComment này thuộc về
    String taskTitle; // Tiêu đề của Task mà TaskComment này thuộc về
    String content; // Nội dung của TaskComment
    String createdByUserName; // Tên của người đã tạo TaskComment
    LocalDateTime createdAt; // Thời gian tạo TaskComment
    LocalDateTime updatedAt; // Thời gian sửa đổi TaskComment
}
