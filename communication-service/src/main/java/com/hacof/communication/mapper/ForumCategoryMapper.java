package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ForumCategoryRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.entity.ForumCategory;
import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.entity.ThreadPost;

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
        forumThreadResponseDTO.setCreatedBy(forumThread.getCreatedBy().getUsername());
        forumThreadResponseDTO.setCreatedDate(forumThread.getCreatedDate().toString());
        forumThreadResponseDTO.setLastModifiedDate(forumThread.getLastModifiedDate().toString());

        // Map ForumCategory of ForumThread (In case ForumThread is related to ForumCategory)
        ForumCategoryResponseDTO forumCategoryResponseDTO = new ForumCategoryResponseDTO();
        forumCategoryResponseDTO.setId(forumThread.getForumCategory().getId());
        forumCategoryResponseDTO.setName(forumThread.getForumCategory().getName());
        forumCategoryResponseDTO.setDescription(forumThread.getForumCategory().getDescription());
        forumCategoryResponseDTO.setSection(forumThread.getForumCategory().getSection());
        forumCategoryResponseDTO.setCreatedDate(forumThread.getForumCategory().getCreatedDate());
        forumCategoryResponseDTO.setLastModifiedDate(forumThread.getForumCategory().getLastModifiedDate());

        forumThreadResponseDTO.setForumCategory(forumCategoryResponseDTO);

//        // Map ThreadPosts for the ForumThread
//        List<ThreadPostResponseDTO> threadPostsDTO = forumThread.getThreadPosts().stream()
//                .map(ForumThreadMapper::toThreadPostResponseDTO)  // Now this will work because the method is public
//                .collect(Collectors.toList());

//        forumThreadResponseDTO.setThreadPosts(threadPostsDTO);

        return forumThreadResponseDTO;
    }

    public static ThreadPostResponseDTO toThreadPostResponseDTO(ThreadPost threadPost) {
        ThreadPostResponseDTO threadPostResponseDTO = new ThreadPostResponseDTO();
        threadPostResponseDTO.setId(threadPost.getId());
        threadPostResponseDTO.setContent(threadPost.getContent());
        threadPostResponseDTO.setCreatedBy(threadPost.getCreatedBy().getUsername());
        threadPostResponseDTO.setCreatedDate(threadPost.getCreatedDate());
        threadPostResponseDTO.setLastModifiedDate(threadPost.getLastModifiedDate());

        return threadPostResponseDTO;
    }

    public static List<ForumThreadResponseDTO> toResponseDTOList(List<ForumThread> entities) {
        return entities.stream().map(ForumThreadMapper::toResponseDTO).collect(Collectors.toList());
    }
}
