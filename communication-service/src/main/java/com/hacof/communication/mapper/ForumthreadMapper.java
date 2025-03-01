package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ForumthreadRequestDTO;
import com.hacof.communication.dto.response.ForumthreadResponseDTO;
import com.hacof.communication.entities.Forumthread;

public class ForumthreadMapper {

    public static Forumthread toEntity(ForumthreadRequestDTO dto) {
        Forumthread forumthread = new Forumthread();
        forumthread.setTitle(dto.getTitle());
        forumthread.setStatus(dto.getStatus());
        return forumthread;
    }

    public static ForumthreadResponseDTO toDTO(Forumthread entity) {
        return new ForumthreadResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy()
        );
    }
}
