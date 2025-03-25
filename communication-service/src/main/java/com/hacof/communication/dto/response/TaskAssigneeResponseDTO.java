package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssigneeResponseDTO {

    private Long id; // ID của TaskAssignee
    private Long taskId; // ID của Task mà TaskAssignee này thuộc về
    private String taskTitle; // Tiêu đề của Task mà TaskAssignee này thuộc về
    private Long userId; // ID của User mà TaskAssignee này liên kết
    private String userUsername; // Username của User mà TaskAssignee này liên kết
    private LocalDateTime createdDate; // Thời gian tạo TaskAssignee
    private LocalDateTime lastModifiedDate; // Thời gian sửa đổi TaskAssignee
}
