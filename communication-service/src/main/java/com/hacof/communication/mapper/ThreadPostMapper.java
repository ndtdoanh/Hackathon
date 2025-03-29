package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.communication.dto.request.ThreadPostRequestDTO;
import com.hacof.communication.dto.response.*;
import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.entity.ThreadPost;

public class ThreadPostMapper {

    // Convert DTO to Entity
    public static ThreadPost toEntity(ThreadPostRequestDTO requestDTO, ForumThread forumThread) {
        return ThreadPost.builder()
                .content(requestDTO.getContent())
                .forumThread(forumThread)
                .build();
    }

    // Convert Entity to Response DTO
    public static ThreadPostResponseDTO toResponseDTO(ThreadPost threadPost) {
        if (threadPost == null) {
            return null;
        }

        ThreadPostResponseDTO responseDTO = new ThreadPostResponseDTO();
        responseDTO.setId(String.valueOf(threadPost.getId()));
        responseDTO.setContent(threadPost.getContent());
        responseDTO.setDeleted(threadPost.isDeleted());
        responseDTO.setCreatedBy(
                threadPost.getCreatedBy() != null ? threadPost.getCreatedBy().getUsername() : null);
        responseDTO.setCreatedDate(threadPost.getCreatedDate());
        responseDTO.setLastModifiedDate(threadPost.getLastModifiedDate());

        // **Mapping ForumThread**
        responseDTO.setForumThread(mapForumThread(threadPost));

        // **Mapping Likes**
        responseDTO.setThreadPostLikes(mapThreadPostLikes(threadPost));

        // **Mapping Reports**
        responseDTO.setThreadPostReports(mapThreadPostReports(threadPost));

        return responseDTO;
    }

    // Convert a List of ThreadPost entities to a List of ThreadPostResponseDTOs
    public static List<ThreadPostResponseDTO> toResponseDTOList(List<ThreadPost> threadPosts) {
        return threadPosts == null
                ? List.of()
                : threadPosts.stream().map(ThreadPostMapper::toResponseDTO).collect(Collectors.toList());
    }

    private static ForumThreadResponseDTO mapForumThread(ThreadPost threadPost) {
        if (threadPost.getForumThread() == null) {
            return null;
        }
        ForumThreadResponseDTO forumThreadDTO = new ForumThreadResponseDTO();
        forumThreadDTO.setId(String.valueOf(threadPost.getForumThread().getId()));
        forumThreadDTO.setTitle(threadPost.getForumThread().getTitle());

        if (threadPost.getForumThread().getForumCategory() != null) {
            forumThreadDTO.setForumCategory(ForumCategoryMapper.toResponseDTO(
                    threadPost.getForumThread().getForumCategory()));
        }

        forumThreadDTO.setCreatedDate(
                threadPost.getForumThread().getCreatedDate().toString());
        forumThreadDTO.setLastModifiedDate(
                threadPost.getForumThread().getLastModifiedDate().toString());

        return forumThreadDTO;
    }

    private static List<ThreadPostLikeResponseDTO> mapThreadPostLikes(ThreadPost threadPost) {
        return threadPost.getThreadPostLikes() == null
                ? List.of()
                : threadPost.getThreadPostLikes().stream()
                        .map(like -> {
                            ThreadPostLikeResponseDTO likeDTO = new ThreadPostLikeResponseDTO();
                            likeDTO.setId(String.valueOf(like.getId()));
                            likeDTO.setCreatedBy(
                                    like.getCreatedBy() != null
                                            ? like.getCreatedBy().getUsername()
                                            : null);
                            likeDTO.setCreatedDate(like.getCreatedDate());
                            likeDTO.setLastModifiedDate(like.getLastModifiedDate());
                            return likeDTO;
                        })
                        .collect(Collectors.toList());
    }

    private static List<ThreadPostReportResponseDTO> mapThreadPostReports(ThreadPost threadPost) {
        return threadPost.getThreadPostReports() == null
                ? List.of()
                : threadPost.getThreadPostReports().stream()
                        .map(report -> {
                            ThreadPostReportResponseDTO reportDTO = new ThreadPostReportResponseDTO();
                            reportDTO.setId(String.valueOf(report.getId()));
                            reportDTO.setReason(report.getReason());
                            reportDTO.setStatus(report.getStatus().toString());
                            reportDTO.setReviewedById(
                                    report.getReviewedBy() != null
                                            ? String.valueOf(
                                                    report.getReviewedBy().getId())
                                            : null);
                            reportDTO.setCreatedDate(report.getCreatedDate());
                            reportDTO.setLastModifiedDate(report.getLastModifiedDate());
                            return reportDTO;
                        })
                        .collect(Collectors.toList());
    }
}
