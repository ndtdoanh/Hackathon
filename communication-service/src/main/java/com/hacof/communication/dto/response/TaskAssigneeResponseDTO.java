package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
