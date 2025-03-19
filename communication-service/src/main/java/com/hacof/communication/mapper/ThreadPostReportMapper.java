package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ThreadPostReportRequestDTO;
import com.hacof.communication.dto.response.ThreadPostReportResponseDTO;
import com.hacof.communication.entity.ThreadPost;
import com.hacof.communication.entity.ThreadPostReport;
import com.hacof.communication.entity.User;
import com.hacof.communication.constant.ThreadPostReportStatus;

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
        responseDTO.setThreadPostId(threadPostReport.getThreadPost().getId());
        responseDTO.setReason(threadPostReport.getReason());
        responseDTO.setStatus(threadPostReport.getStatus().name());
        responseDTO.setReviewedById(threadPostReport.getReviewedBy() != null ? threadPostReport.getReviewedBy().getId() : null);
        responseDTO.setCreatedDate(threadPostReport.getCreatedDate());
        responseDTO.setLastModifiedDate(threadPostReport.getLastModifiedDate());
        return responseDTO;
    }
}
