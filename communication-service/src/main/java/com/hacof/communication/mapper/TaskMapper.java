package com.hacof.communication.mapper;

import com.hacof.communication.dto.response.TaskResponseDTO;
import com.hacof.communication.entities.Task;

public class TaskMapper {
    public static TaskResponseDTO toDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setComment(task.getComment());
        dto.setDocumentUrl(task.getDocumentUrl());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDeadline(task.getDeadline());
        dto.setAssignedToId(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null);
        dto.setMentorId(task.getMentor() != null ? task.getMentor().getId() : null);
        dto.setListName(task.getListName());
        dto.setBoardName(task.getBoardName());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setCreatedBy(task.getCreatedBy());
        dto.setUpdatedBy(task.getUpdatedBy());
        return dto;
    }
}

