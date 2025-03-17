package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ThreadPostRequestDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.entity.User;

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
        responseDTO.setForumThreadId(threadPost.getForumThread().getId());
        responseDTO.setContent(threadPost.getContent());
        responseDTO.setDeleted(threadPost.isDeleted());
        responseDTO.setCreatedBy(threadPost.getCreatedBy().getUsername());
        responseDTO.setCreatedDate(threadPost.getCreatedDate());
        responseDTO.setLastModifiedDate(threadPost.getLastModifiedDate());  // Optionally set last modified date
        return responseDTO;
    }
}
