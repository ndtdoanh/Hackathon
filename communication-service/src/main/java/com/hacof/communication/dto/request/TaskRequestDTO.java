package com.hacof.communication.dto.request;

import java.time.LocalDate;

import com.hacof.communication.enums.Priority;
import com.hacof.communication.enums.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TaskRequestDTO {
    private String name;
    private String description;
    private String comment;
    private String documentUrl;
    private Status status;
    private Priority priority;
    private LocalDate deadline;
    private Long assignedToId;
    private Long mentorId;
    private String listName;
    private String boardName;
}
