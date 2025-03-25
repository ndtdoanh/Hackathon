package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ForumCategoryRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.entity.ForumCategory;
import com.hacof.communication.entity.ForumThread;

import java.util.List;
import java.util.stream.Collectors;

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

        // Map forumThreads to ForumThreadResponseDTO
        List<ForumThreadResponseDTO> forumThreadsDTO = entity.getForumThreads().stream()
                .map(ForumCategoryMapper::toForumThreadResponseDTO)  // Convert each ForumThread to ForumThreadResponseDTO
                .collect(Collectors.toList());

        responseDTO.setForumThreads(forumThreadsDTO);

        return responseDTO;
    }

    // Helper method to map ForumThread entity to ForumThreadResponseDTO
    private static ForumThreadResponseDTO toForumThreadResponseDTO(ForumThread forumThread) {
        ForumThreadResponseDTO forumThreadResponseDTO = new ForumThreadResponseDTO();
        forumThreadResponseDTO.setId(forumThread.getId());
        forumThreadResponseDTO.setTitle(forumThread.getTitle());
        forumThreadResponseDTO.setCreatedDate(String.valueOf(forumThread.getCreatedDate()));
        forumThreadResponseDTO.setLastModifiedDate(String.valueOf(forumThread.getLastModifiedDate()));
        return forumThreadResponseDTO;
    }
}
