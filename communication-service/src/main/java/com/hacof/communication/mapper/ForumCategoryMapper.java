package com.hacof.communication.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hacof.communication.dto.request.ForumCategoryRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.entity.ForumCategory;
import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.entity.ThreadPost;

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
        responseDTO.setId(String.valueOf(entity.getId()));
        responseDTO.setName(entity.getName());
        responseDTO.setDescription(entity.getDescription());
        responseDTO.setSection(entity.getSection());
        responseDTO.setCreatedAt(entity.getCreatedDate());
        responseDTO.setUpdatedAt(entity.getLastModifiedDate());

        // Kiểm tra null cho forumThreads
        List<ForumThreadResponseDTO> forumThreadsDTO = (entity.getForumThreads() != null)
                ? entity.getForumThreads().stream()
                        .map(ForumCategoryMapper::toForumThreadResponseDTO)
                        .collect(Collectors.toList())
                : new ArrayList<>(); // Trường hợp forumThreads là null, trả về danh sách rỗng

        responseDTO.setForumThreads(forumThreadsDTO);

        return responseDTO;
    }

    // Helper method to map ForumThread entity to ForumThreadResponseDTO
    private static ForumThreadResponseDTO toForumThreadResponseDTO(ForumThread forumThread) {
        ForumThreadResponseDTO forumThreadResponseDTO = new ForumThreadResponseDTO();
        forumThreadResponseDTO.setId(String.valueOf(forumThread.getId()));
        forumThreadResponseDTO.setTitle(forumThread.getTitle());
        forumThreadResponseDTO.setCreatedByUserName(forumThread.getCreatedBy().getUsername());
        forumThreadResponseDTO.setCreatedAt(forumThread.getCreatedDate().toString());
        forumThreadResponseDTO.setUpdatedAt(forumThread.getLastModifiedDate().toString());

        // Map ForumCategory of ForumThread (In case ForumThread is related to ForumCategory)
        ForumCategoryResponseDTO forumCategoryResponseDTO = new ForumCategoryResponseDTO();
        forumCategoryResponseDTO.setId(
                String.valueOf(forumThread.getForumCategory().getId()));
        forumCategoryResponseDTO.setName(forumThread.getForumCategory().getName());
        forumCategoryResponseDTO.setDescription(forumThread.getForumCategory().getDescription());
        forumCategoryResponseDTO.setSection(forumThread.getForumCategory().getSection());
        forumCategoryResponseDTO.setCreatedAt(forumThread.getForumCategory().getCreatedDate());
        forumCategoryResponseDTO.setUpdatedAt(forumThread.getForumCategory().getLastModifiedDate());

        forumThreadResponseDTO.setForumCategoryId(
                String.valueOf(forumThread.getForumCategory().getId()));

        return forumThreadResponseDTO;
    }

    public static ThreadPostResponseDTO toThreadPostResponseDTO(ThreadPost threadPost) {
        ThreadPostResponseDTO threadPostResponseDTO = new ThreadPostResponseDTO();
        threadPostResponseDTO.setId(String.valueOf(threadPost.getId()));
        threadPostResponseDTO.setContent(threadPost.getContent());
        threadPostResponseDTO.setCreatedByUserName(threadPost.getCreatedBy().getUsername());
        threadPostResponseDTO.setCreatedAt(threadPost.getCreatedDate());
        threadPostResponseDTO.setUpdatedAt(threadPost.getLastModifiedDate());

        return threadPostResponseDTO;
    }

    public static List<ForumThreadResponseDTO> toResponseDTOList(List<ForumThread> entities) {
        return entities.stream().map(ForumThreadMapper::toResponseDTO).collect(Collectors.toList());
    }
}
