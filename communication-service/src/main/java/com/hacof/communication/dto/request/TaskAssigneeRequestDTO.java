package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskAssigneeRequestDTO {
    private Long taskId;
    private Long userId;
}
