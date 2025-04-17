package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.communication.dto.request.ThreadPostLikeRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.dto.response.ThreadPostLikeResponseDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.entity.ThreadPostLike;
import com.hacof.communication.entity.User;

public class ThreadPostLikeMapper {

    public static ThreadPostLike toEntity(ThreadPostLikeRequestDTO requestDTO, ThreadPost threadPost, User createdBy) {
        ThreadPostLike threadPostLike = new ThreadPostLike();
        threadPostLike.setThreadPost(threadPost);
        threadPostLike.setCreatedBy(createdBy);
        return threadPostLike;
    }

    public static ThreadPostLikeResponseDTO toResponseDTO(ThreadPostLike threadPostLike) {
        ThreadPostLikeResponseDTO responseDTO = new ThreadPostLikeResponseDTO();
        responseDTO.setId((String.valueOf(threadPostLike.getId())));

        // Map the entire ThreadPost entity to ThreadPostResponseDTO
        ThreadPostResponseDTO threadPostResponseDTO = new ThreadPostResponseDTO();
        threadPostResponseDTO.setId(
                String.valueOf(threadPostLike.getThreadPost().getId()));
        threadPostResponseDTO.setContent(threadPostLike.getThreadPost().getContent());
        threadPostResponseDTO.setCreatedByUserName(
                threadPostLike.getThreadPost().getCreatedBy().getUsername());
        threadPostResponseDTO.setCreatedAt(threadPostLike.getThreadPost().getCreatedDate());
        threadPostResponseDTO.setUpdatedAt(threadPostLike.getThreadPost().getLastModifiedDate());

        // Map the ForumThread entity to ForumThreadResponseDTO
        ForumThreadResponseDTO forumThreadResponseDTO = new ForumThreadResponseDTO();
        forumThreadResponseDTO.setId(
                String.valueOf(threadPostLike.getThreadPost().getForumThread().getId()));
        forumThreadResponseDTO.setTitle(
                threadPostLike.getThreadPost().getForumThread().getTitle());
        forumThreadResponseDTO.setCreatedByUserName(
                threadPostLike.getThreadPost().getForumThread().getCreatedBy().getUsername());
        forumThreadResponseDTO.setCreatedAt(
                String.valueOf(threadPostLike.getThreadPost().getForumThread().getCreatedDate()));
        forumThreadResponseDTO.setUpdatedAt(
                String.valueOf(threadPostLike.getThreadPost().getForumThread().getLastModifiedDate()));

        // Map ForumCategory inside ForumThread
        ForumCategoryResponseDTO forumCategoryResponseDTO = new ForumCategoryResponseDTO();
        forumCategoryResponseDTO.setId(String.valueOf(threadPostLike
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getId()));
        forumCategoryResponseDTO.setName(threadPostLike
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getName());
        forumCategoryResponseDTO.setDescription(threadPostLike
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getDescription());
        forumCategoryResponseDTO.setSection(threadPostLike
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getSection());
        forumCategoryResponseDTO.setCreatedAt(threadPostLike
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getCreatedDate());
        forumCategoryResponseDTO.setUpdatedAt(threadPostLike
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getLastModifiedDate());

        // Set ForumCategory into ForumThreadResponseDTO
        forumThreadResponseDTO.setForumCategory(forumCategoryResponseDTO);

        // Map ThreadPosts (List) in ForumThread
        List<ThreadPostResponseDTO> threadPostList =
                threadPostLike.getThreadPost().getForumThread().getThreadPosts().stream()
                        .map(ThreadPostMapper::toResponseDTO) // Use the existing method to map ThreadPost to
                        // ThreadPostResponseDTO
                        .collect(Collectors.toList());

        // Set ThreadPosts into ForumThreadResponseDTO
        forumThreadResponseDTO.setThreadPosts(threadPostList);

        // Set ForumThread into ThreadPostResponseDTO
        threadPostResponseDTO.setForumThread(forumThreadResponseDTO);

        // Set the full ThreadPostResponseDTO in the response DTO
        responseDTO.setThreadPost(threadPostResponseDTO);

        // Set remaining ThreadPostLike details
        responseDTO.setCreatedByUserName(
                threadPostLike.getCreatedBy().getUsername()); // Assuming createdBy is a User entity
        responseDTO.setCreatedAt(threadPostLike.getCreatedDate());
        responseDTO.setUpdatedAt(threadPostLike.getLastModifiedDate());

        return responseDTO;
    }
}
