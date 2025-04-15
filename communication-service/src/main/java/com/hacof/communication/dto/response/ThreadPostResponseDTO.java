package com.hacof.communication.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ThreadPostResponseDTO {

    String id;
    ForumThreadResponseDTO forumThread;
    String content;
    boolean isDeleted;
    String createdByUserName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<ThreadPostLikeResponseDTO> threadPostLikes;
    List<ThreadPostReportResponseDTO> threadPostReports;
}
