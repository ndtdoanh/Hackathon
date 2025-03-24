package com.hacof.communication.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskLabelResponseDTO {

    private Long id;
    private Long taskId;
    private String taskTitle;
    private Long boardLabelId;
    private String boardLabelName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
