package com.hacof.communication.mapper;

import com.hacof.communication.constant.ThreadPostReportStatus;
import com.hacof.communication.dto.request.ThreadPostReportRequestDTO;
import com.hacof.communication.dto.response.ThreadPostReportResponseDTO;
import com.hacof.communication.dto.response.ThreadPostResponseDTO;  // Import ThreadPostResponseDTO
import com.hacof.communication.dto.response.ForumThreadResponseDTO;  // Import ForumThreadResponseDTO
import com.hacof.communication.dto.response.ForumCategoryResponseDTO;  // Import ForumCategoryResponseDTO
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.entity.ThreadPostReport;
import com.hacof.communication.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ThreadPostReportMapper {

    public static ThreadPostReport toEntity(ThreadPostReportRequestDTO requestDTO, ThreadPost threadPost, User createdBy) {
        return ThreadPostReport.builder()
                .threadPost(threadPost)
                .reason(requestDTO.getReason())
                .status(ThreadPostReportStatus.PENDING)  // Default status could be PENDING
                .build();
    }

    public static ThreadPostReportResponseDTO toResponseDTO(ThreadPostReport threadPostReport) {
        ThreadPostReportResponseDTO responseDTO = new ThreadPostReportResponseDTO();
        responseDTO.setId(threadPostReport.getId());

        // Map the entire ThreadPost entity to ThreadPostResponseDTO
        ThreadPostResponseDTO threadPostResponseDTO = new ThreadPostResponseDTO();
        threadPostResponseDTO.setId(threadPostReport.getThreadPost().getId());
        threadPostResponseDTO.setContent(threadPostReport.getThreadPost().getContent());
        threadPostResponseDTO.setCreatedBy(threadPostReport.getThreadPost().getCreatedBy().getUsername());
        threadPostResponseDTO.setCreatedDate(threadPostReport.getThreadPost().getCreatedDate());
        threadPostResponseDTO.setLastModifiedDate(threadPostReport.getThreadPost().getLastModifiedDate());

        // Map the ForumThread entity to ForumThreadResponseDTO
        ForumThreadResponseDTO forumThreadResponseDTO = new ForumThreadResponseDTO();
        forumThreadResponseDTO.setId(threadPostReport.getThreadPost().getForumThread().getId());
        forumThreadResponseDTO.setTitle(threadPostReport.getThreadPost().getForumThread().getTitle());
        forumThreadResponseDTO.setCreatedBy(threadPostReport.getThreadPost().getForumThread().getCreatedBy().getUsername());
        forumThreadResponseDTO.setCreatedDate(String.valueOf(threadPostReport.getThreadPost().getForumThread().getCreatedDate()));
        forumThreadResponseDTO.setLastModifiedDate(String.valueOf(threadPostReport.getThreadPost().getForumThread().getLastModifiedDate()));

        // Map ForumCategory inside ForumThread
        ForumCategoryResponseDTO forumCategoryResponseDTO = new ForumCategoryResponseDTO();
        forumCategoryResponseDTO.setId(threadPostReport.getThreadPost().getForumThread().getForumCategory().getId());
        forumCategoryResponseDTO.setName(threadPostReport.getThreadPost().getForumThread().getForumCategory().getName());
        forumCategoryResponseDTO.setDescription(threadPostReport.getThreadPost().getForumThread().getForumCategory().getDescription());
        forumCategoryResponseDTO.setSection(threadPostReport.getThreadPost().getForumThread().getForumCategory().getSection());
        forumCategoryResponseDTO.setCreatedDate(threadPostReport.getThreadPost().getForumThread().getForumCategory().getCreatedDate());
        forumCategoryResponseDTO.setLastModifiedDate(threadPostReport.getThreadPost().getForumThread().getForumCategory().getLastModifiedDate());

        // Map ThreadPosts (List) in ForumThread
        List<ThreadPostResponseDTO> threadPostList = threadPostReport.getThreadPost().getForumThread().getThreadPosts().stream()
                .map(ThreadPostMapper::toResponseDTO) // Use the existing method to map ThreadPost to ThreadPostResponseDTO
                .collect(Collectors.toList());

        // Set ThreadPosts into ForumThreadResponseDTO
        forumThreadResponseDTO.setThreadPosts(threadPostList);

        // Set ForumCategory into ForumThreadResponseDTO
        forumThreadResponseDTO.setForumCategory(forumCategoryResponseDTO);

        // Set ThreadPost into ThreadPostResponseDTO
        threadPostResponseDTO.setForumThread(forumThreadResponseDTO);

        // Set the full ThreadPostResponseDTO in the response DTO
        responseDTO.setThreadPost(threadPostResponseDTO);

        // Set the other report-related fields
        responseDTO.setReason(threadPostReport.getReason());
        responseDTO.setStatus(threadPostReport.getStatus().name());  // Convert status to string (PENDING, RESOLVED, etc.)
        responseDTO.setReviewedById(threadPostReport.getReviewedBy() != null ? threadPostReport.getReviewedBy().getId() : null);
        responseDTO.setCreatedDate(threadPostReport.getCreatedDate());
        responseDTO.setLastModifiedDate(threadPostReport.getLastModifiedDate());

        return responseDTO;
    }
}
