package com.hacof.communication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.communication.constant.ThreadPostReportStatus;
import com.hacof.communication.dto.request.ThreadPostReportRequestDTO;
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;
import com.hacof.communication.dto.response.ForumThreadResponseDTO;
import com.hacof.communication.dto.response.ThreadPostReportResponseDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.entity.ThreadPostReport;
import com.hacof.communication.entity.User;

public class ThreadPostReportMapper {

    // Convert DTO to Entity
    public static ThreadPostReport toEntity(
            ThreadPostReportRequestDTO requestDTO, ThreadPost threadPost, User reviewedBy) {
        return ThreadPostReport.builder()
                .threadPost(threadPost)
                .reason(requestDTO.getReason())
                .status(ThreadPostReportStatus.PENDING) // Default status could be PENDING
                .reviewedBy(reviewedBy)
                .build();
    }

    // Convert Entity to Response DTO
    public static ThreadPostReportResponseDTO toResponseDTO(ThreadPostReport threadPostReport) {
        ThreadPostReportResponseDTO responseDTO = new ThreadPostReportResponseDTO();
        responseDTO.setId(String.valueOf(threadPostReport.getId()));

        // Map the entire ThreadPost entity to ThreadPostResponseDTO
        ThreadPostResponseDTO threadPostResponseDTO = new ThreadPostResponseDTO();
        threadPostResponseDTO.setId(
                String.valueOf(threadPostReport.getThreadPost().getId()));
        threadPostResponseDTO.setContent(threadPostReport.getThreadPost().getContent());
        threadPostResponseDTO.setCreatedBy(
                threadPostReport.getThreadPost().getCreatedBy().getUsername());
        threadPostResponseDTO.setCreatedDate(threadPostReport.getThreadPost().getCreatedDate());
        threadPostResponseDTO.setLastModifiedDate(
                threadPostReport.getThreadPost().getLastModifiedDate());

        // Map the ForumThread entity to ForumThreadResponseDTO
        ForumThreadResponseDTO forumThreadResponseDTO = new ForumThreadResponseDTO();
        forumThreadResponseDTO.setId(
                String.valueOf(threadPostReport.getThreadPost().getForumThread().getId()));
        forumThreadResponseDTO.setTitle(
                threadPostReport.getThreadPost().getForumThread().getTitle());
        forumThreadResponseDTO.setCreatedBy(
                threadPostReport.getThreadPost().getForumThread().getCreatedBy().getUsername());
        forumThreadResponseDTO.setCreatedDate(
                String.valueOf(threadPostReport.getThreadPost().getForumThread().getCreatedDate()));
        forumThreadResponseDTO.setLastModifiedDate(
                String.valueOf(threadPostReport.getThreadPost().getForumThread().getLastModifiedDate()));

        // Map ForumCategory inside ForumThread
        ForumCategoryResponseDTO forumCategoryResponseDTO = new ForumCategoryResponseDTO();
        forumCategoryResponseDTO.setId(String.valueOf(threadPostReport
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getId()));
        forumCategoryResponseDTO.setName(threadPostReport
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getName());
        forumCategoryResponseDTO.setDescription(threadPostReport
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getDescription());
        forumCategoryResponseDTO.setSection(threadPostReport
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getSection());
        forumCategoryResponseDTO.setCreatedDate(threadPostReport
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getCreatedDate());
        forumCategoryResponseDTO.setLastModifiedDate(threadPostReport
                .getThreadPost()
                .getForumThread()
                .getForumCategory()
                .getLastModifiedDate());

        // Set ForumCategory into ForumThreadResponseDTO
        forumThreadResponseDTO.setForumCategory(forumCategoryResponseDTO);

        // Map ThreadPosts (List) in ForumThread
        List<ThreadPostResponseDTO> threadPostList =
                threadPostReport.getThreadPost().getForumThread().getThreadPosts().stream()
                        .map(ThreadPostMapper::toResponseDTO) // Use the existing method to map ThreadPost to
                        // ThreadPostResponseDTO
                        .collect(Collectors.toList());

        // Set ThreadPosts into ForumThreadResponseDTO
        forumThreadResponseDTO.setThreadPosts(threadPostList);

        // Set ForumThread into ThreadPostResponseDTO
        threadPostResponseDTO.setForumThread(forumThreadResponseDTO);

        // Set the full ThreadPostResponseDTO in the response DTO
        responseDTO.setThreadPost(threadPostResponseDTO);

        // Set the other report-related fields
        responseDTO.setReason(threadPostReport.getReason());
        responseDTO.setStatus(
                threadPostReport.getStatus().name()); // Convert status to string (PENDING, RESOLVED, etc.)
        responseDTO.setReviewedById(
                threadPostReport.getReviewedBy() != null
                        ? String.valueOf(threadPostReport.getReviewedBy().getId())
                        : null);
        responseDTO.setCreatedDate(threadPostReport.getCreatedDate());
        responseDTO.setLastModifiedDate(threadPostReport.getLastModifiedDate());

        return responseDTO;
    }
}
