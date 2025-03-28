package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadPostResponseDTO {

    private Long id;
    private ForumThreadResponseDTO forumThread;
    private String content;
    private boolean isDeleted;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<ThreadPostLikeResponseDTO> threadPostLikes;
    private List<ThreadPostReportResponseDTO> threadPostReports;
}
