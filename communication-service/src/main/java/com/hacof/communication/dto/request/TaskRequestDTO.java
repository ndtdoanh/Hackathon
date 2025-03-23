package com.hacof.communication.dto.request;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

    private String title;
    private String description;
    private int position;
    private Long boardListId;
    private LocalDateTime dueDate;
}
