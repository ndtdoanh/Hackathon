package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCommentResponseDTO {

    private Long id;            // ID của TaskComment
    private Long taskId;        // ID của Task mà TaskComment này thuộc về
    private String taskTitle;   // Tiêu đề của Task mà TaskComment này thuộc về
    private String content;     // Nội dung của TaskComment
    private String createdBy;   // Tên của người đã tạo TaskComment
    private LocalDateTime createdDate;  // Thời gian tạo TaskComment
    private LocalDateTime lastModifiedDate; // Thời gian sửa đổi TaskComment

}
