package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskAssigneeResponseDTO {

    String id; // ID của TaskAssignee
    String taskId; // ID của Task mà TaskAssignee này thuộc về
    String taskTitle; // Tiêu đề của Task mà TaskAssignee này thuộc về
    String userId; // ID của User mà TaskAssignee này liên kết
    String userUsername; // Username của User mà TaskAssignee này liên kết
    LocalDateTime createdDate; // Thời gian tạo TaskAssignee
    LocalDateTime lastModifiedDate; // Thời gian sửa đổi TaskAssignee
}
