package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.communication.dto.request.ForumThreadRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.entity.ForumCategory;
import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.entity.ThreadPost;

public class ForumThreadMapper {

    // Method to convert ForumThreadRequestDTO to ForumThread entity
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
        responseDTO.setId(String.valueOf(entity.getId()));
        responseDTO.setTitle(entity.getTitle());

        // Map ForumCategory entity to ForumCategoryResponseDTO
        ForumCategoryResponseDTO forumCategoryResponseDTO = mapForumCategoryToResponseDTO(entity.getForumCategory());
        responseDTO.setForumCategory(forumCategoryResponseDTO);

        responseDTO.setLocked(entity.isLocked());
        responseDTO.setPinned(entity.isPinned());
        responseDTO.setCreatedBy(entity.getCreatedBy().getUsername()); // Assuming createdBy is a User entity
        responseDTO.setCreatedDate(entity.getCreatedDate().toString());
        responseDTO.setLastModifiedDate(entity.getLastModifiedDate().toString());

        // Map List of ThreadPost to List of ThreadPostResponseDTO
        // Ensure threadPosts is not null to avoid NullPointerException
        List<ThreadPostResponseDTO> threadPostResponseDTOList = (entity.getThreadPosts() == null ?
                List.of() : entity.getThreadPosts().stream()
                .map(ForumThreadMapper::toThreadPostResponseDTO)
                .collect(Collectors.toList()));

        responseDTO.setThreadPosts(threadPostResponseDTOList);

        return responseDTO;
    }

    // Helper method to map ForumCategory entity to ForumCategoryResponseDTO
    private static ForumCategoryResponseDTO mapForumCategoryToResponseDTO(ForumCategory forumCategory) {
        ForumCategoryResponseDTO forumCategoryResponseDTO = new ForumCategoryResponseDTO();
        forumCategoryResponseDTO.setId(String.valueOf(forumCategory.getId()));
        forumCategoryResponseDTO.setName(forumCategory.getName());
        forumCategoryResponseDTO.setDescription(forumCategory.getDescription());
        forumCategoryResponseDTO.setSection(forumCategory.getSection());
        forumCategoryResponseDTO.setCreatedDate(forumCategory.getCreatedDate());
        forumCategoryResponseDTO.setLastModifiedDate(forumCategory.getLastModifiedDate());
        return forumCategoryResponseDTO;
    }

    // Helper method to map ThreadPost entity to ThreadPostResponseDTO
    private static ThreadPostResponseDTO toThreadPostResponseDTO(ThreadPost threadPost) {
        ThreadPostResponseDTO threadPostResponseDTO = new ThreadPostResponseDTO();
        threadPostResponseDTO.setId(String.valueOf(threadPost.getId()));
        threadPostResponseDTO.setContent(threadPost.getContent());
        threadPostResponseDTO.setCreatedBy(threadPost.getCreatedBy().getUsername());
        threadPostResponseDTO.setCreatedDate(threadPost.getCreatedDate());
        threadPostResponseDTO.setLastModifiedDate(threadPost.getLastModifiedDate());

        return threadPostResponseDTO;
    }

    // Method to convert List<ForumThread> entities to List<ForumThreadResponseDTO>
    public static List<ForumThreadResponseDTO> toResponseDTOList(List<ForumThread> entities) {
        return entities.stream().map(ForumThreadMapper::toResponseDTO).collect(Collectors.toList());
    }
}
