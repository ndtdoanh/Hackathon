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
public class TaskResponseDTO {

    String id;
    String title;
    String description;
    int position;
    String boardListName;
    LocalDateTime dueDate;
    String createdBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
