package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ForumCategoryRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.entity.ForumCategory;

public class ForumCategoryMapper {

    public static ForumCategory toEntity(ForumCategoryRequestDTO requestDTO) {
        return ForumCategory.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .section(requestDTO.getSection())
                .build();
    }

    public static ForumCategoryResponseDTO toResponseDTO(ForumCategory entity) {
        ForumCategoryResponseDTO responseDTO = new ForumCategoryResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setName(entity.getName());
        responseDTO.setDescription(entity.getDescription());
        responseDTO.setSection(entity.getSection());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setLastModifiedDate(entity.getLastModifiedDate());
        return responseDTO;
    }
}
