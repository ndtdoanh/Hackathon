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
                .isLocked(dto.getIsLocked()) // Use getLocked() to access the 'locked' field
                .isPinned(dto.getIsPinned()) // directly access the primitive value
                .build();
    }

    public static ForumThreadResponseDTO toResponseDTO(ForumThread entity) {
        ForumThreadResponseDTO responseDTO = new ForumThreadResponseDTO();
        responseDTO.setId(String.valueOf(entity.getId()));
        responseDTO.setTitle(entity.getTitle());

        // Map ForumCategory entity to ForumCategoryResponseDTO
        ForumCategoryResponseDTO forumCategoryResponseDTO = mapForumCategoryToResponseDTO(entity.getForumCategory());
        responseDTO.setForumCategoryId(String.valueOf(entity.getForumCategory().getId()));

        responseDTO.setIsLocked(entity.isLocked());
        responseDTO.setIsPinned(entity.isPinned());
        responseDTO.setCreatedByUserName(entity.getCreatedBy().getUsername()); // Assuming createdBy is a User entity
        responseDTO.setCreatedAt(entity.getCreatedDate().toString());
        responseDTO.setUpdatedAt(entity.getLastModifiedDate().toString());

        // Map List of ThreadPost to List of ThreadPostResponseDTO
        // Ensure threadPosts is not null to avoid NullPointerException
        List<ThreadPostResponseDTO> threadPostResponseDTOList = (entity.getThreadPosts() == null
                ? List.of()
                : entity.getThreadPosts().stream()
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
        forumCategoryResponseDTO.setCreatedAt(forumCategory.getCreatedDate());
        forumCategoryResponseDTO.setUpdatedAt(forumCategory.getLastModifiedDate());
        return forumCategoryResponseDTO;
    }

    // Helper method to map ThreadPost entity to ThreadPostResponseDTO
    private static ThreadPostResponseDTO toThreadPostResponseDTO(ThreadPost threadPost) {
        ThreadPostResponseDTO threadPostResponseDTO = new ThreadPostResponseDTO();
        threadPostResponseDTO.setId(String.valueOf(threadPost.getId()));
        threadPostResponseDTO.setContent(threadPost.getContent());
        threadPostResponseDTO.setCreatedByUserName(threadPost.getCreatedBy().getUsername());
        threadPostResponseDTO.setCreatedAt(threadPost.getCreatedDate());
        threadPostResponseDTO.setUpdatedAt(threadPost.getLastModifiedDate());

        return threadPostResponseDTO;
    }

    // Method to convert List<ForumThread> entities to List<ForumThreadResponseDTO>
    public static List<ForumThreadResponseDTO> toResponseDTOList(List<ForumThread> entities) {
        return entities.stream().map(ForumThreadMapper::toResponseDTO).collect(Collectors.toList());
    }
}
