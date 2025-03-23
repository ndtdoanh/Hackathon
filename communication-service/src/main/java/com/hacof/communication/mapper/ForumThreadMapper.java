package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.communication.dto.request.ForumThreadRequestDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.entity.ForumCategory;
import com.hacof.communication.entity.ForumThread;

public class ForumThreadMapper {

    public static ForumThread toEntity(ForumThreadRequestDTO dto, ForumCategory forumCategory) {
        return ForumThread.builder()
                .title(dto.getTitle())
                .forumCategory(forumCategory)
                .isLocked(dto.isLocked())
                .isPinned(dto.isPinned())
                .build();
    }

    public static ForumThreadResponseDTO toResponseDTO(ForumThread entity) {
        ForumThreadResponseDTO responseDTO = new ForumThreadResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setTitle(entity.getTitle());
        responseDTO.setForumCategoryName(entity.getForumCategory().getName());
        responseDTO.setLocked(entity.isLocked());
        responseDTO.setPinned(entity.isPinned());
        responseDTO.setCreatedBy(entity.getCreatedBy().getUsername());
        responseDTO.setCreatedDate(entity.getCreatedDate().toString());
        responseDTO.setLastModifiedDate(entity.getLastModifiedDate().toString());
        return responseDTO;
    }

    public static List<ForumThreadResponseDTO> toResponseDTOList(List<ForumThread> entities) {
        return entities.stream().map(ForumThreadMapper::toResponseDTO).collect(Collectors.toList());
    }
}
