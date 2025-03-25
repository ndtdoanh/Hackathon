package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ThreadPostRequestDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.entity.ThreadPost;
import java.util.List;
import java.util.stream.Collectors;

public class ThreadPostMapper {

    // Convert DTO to Entity
    public static ThreadPost toEntity(ThreadPostRequestDTO requestDTO, ForumThread forumThread) {
        ThreadPost threadPost = new ThreadPost();
        threadPost.setContent(requestDTO.getContent());
        threadPost.setForumThread(forumThread);
        return threadPost;
    }

    // Convert Entity to Response DTO
    public static ThreadPostResponseDTO toResponseDTO(ThreadPost threadPost) {
        ThreadPostResponseDTO responseDTO = new ThreadPostResponseDTO();
        responseDTO.setId(threadPost.getId());

        // Map the ForumThread entity to ForumThreadResponseDTO
        ForumThreadResponseDTO forumThreadResponseDTO = new ForumThreadResponseDTO();
        forumThreadResponseDTO.setId(threadPost.getForumThread().getId());
        forumThreadResponseDTO.setTitle(threadPost.getForumThread().getTitle());

        // Map ForumCategory in ForumThread
        forumThreadResponseDTO.setForumCategory(
                ForumCategoryMapper.toResponseDTO(threadPost.getForumThread().getForumCategory())
        );

        // Set the ForumThread details
        forumThreadResponseDTO.setCreatedDate(threadPost.getForumThread().getCreatedDate().toString());
        forumThreadResponseDTO.setLastModifiedDate(threadPost.getForumThread().getLastModifiedDate().toString());

        // Set the full ForumThreadResponseDTO in ThreadPostResponseDTO
        responseDTO.setForumThread(forumThreadResponseDTO);

        // Set remaining ThreadPost details
        responseDTO.setContent(threadPost.getContent());
        responseDTO.setDeleted(threadPost.isDeleted());
        responseDTO.setCreatedBy(threadPost.getCreatedBy().getUsername());

        // Convert LocalDateTime to String for ThreadPost dates
        responseDTO.setCreatedDate(threadPost.getCreatedDate());
        if (threadPost.getLastModifiedDate() != null) {
            responseDTO.setLastModifiedDate(threadPost.getLastModifiedDate());
        }

        return responseDTO;
    }

    // Convert a List of ThreadPost entities to a List of ThreadPostResponseDTOs
    public static List<ThreadPostResponseDTO> toResponseDTOList(List<ThreadPost> threadPosts) {
        return threadPosts.stream()
                .map(ThreadPostMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
