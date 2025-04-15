package com.hacof.communication.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

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
