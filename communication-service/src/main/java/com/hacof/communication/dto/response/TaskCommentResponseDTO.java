package com.hacof.communication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    String createdBy; // Tên của người đã tạo TaskComment
    LocalDateTime createdDate; // Thời gian tạo TaskComment
    LocalDateTime lastModifiedDate; // Thời gian sửa đổi TaskComment
}
