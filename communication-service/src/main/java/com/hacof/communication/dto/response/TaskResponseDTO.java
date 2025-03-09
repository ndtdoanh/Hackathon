package com.hacof.communication.dto.response;

import java.time.Instant;
import java.time.LocalDate;

import com.hacof.communication.entities.Task;
import com.hacof.communication.enums.Priority;
import com.hacof.communication.enums.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TaskResponseDTO {
    private Long id;
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
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    public TaskResponseDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.comment = task.getComment();
        this.documentUrl = task.getDocumentUrl();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.deadline = task.getDeadline();
        this.assignedToId = task.getAssignedTo() != null ? task.getAssignedTo().getId() : null;
        this.mentorId = task.getMentor() != null ? task.getMentor().getId() : null;
        this.listName = task.getListName();
        this.boardName = task.getBoardName();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
        this.createdBy = task.getCreatedBy();
        this.updatedBy = task.getUpdatedBy();
    }
}
