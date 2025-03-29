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
public class TaskLabelResponseDTO {

    String id;
    String taskId;
    String taskTitle;
    String boardLabelId;
    String boardLabelName;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
