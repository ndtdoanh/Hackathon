package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCommentRequestDTO {
    private Long taskId;
    private String content;
}
