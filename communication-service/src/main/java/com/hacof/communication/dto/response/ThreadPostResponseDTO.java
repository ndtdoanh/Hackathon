package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadPostResponseDTO {

    String id;
    ForumThreadResponseDTO forumThread;
    String content;
    boolean isDeleted;
    String createdBy;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
    List<ThreadPostLikeResponseDTO> threadPostLikes;
    List<ThreadPostReportResponseDTO> threadPostReports;
}
