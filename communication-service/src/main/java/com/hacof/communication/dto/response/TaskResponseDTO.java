package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

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
    String boardListId;
    LocalDateTime dueDate;
    List<String> fileUrls;
    String createdBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
