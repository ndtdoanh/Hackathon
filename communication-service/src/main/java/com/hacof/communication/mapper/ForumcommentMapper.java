package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ForumcommentRequestDTO;
import com.hacof.communication.dto.response.ForumcommentResponseDTO;
import com.hacof.communication.entities.Forumcomment;

public class ForumcommentMapper {

    public static Forumcomment toEntity(ForumcommentRequestDTO dto) {
        Forumcomment forumcomment = new Forumcomment();
        forumcomment.setComment(dto.getComment());
        forumcomment.setStatus(dto.getStatus());
        return forumcomment;
    }

    public static ForumcommentResponseDTO toDTO(Forumcomment entity) {
        return new ForumcommentResponseDTO(
                entity.getId(),
                entity.getComment(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy());
    }
}
