package com.hacof.communication.dto.request;

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
public class TaskRequestDTO {

    String title;
    String description;
    int position;
    String boardListId;
    LocalDateTime dueDate;
    List<String> fileUrls;
}
