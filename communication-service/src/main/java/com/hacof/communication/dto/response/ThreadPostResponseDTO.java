package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadPostResponseDTO {

    String id;
//    ForumThreadResponseDTO forumThread;
    String forumThreadId;
    String content;
    boolean isDeleted;
    String createdByUserName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String deletedById;
    List<ThreadPostLikeResponseDTO> threadPostLikes;
    List<ThreadPostReportResponseDTO> threadPostReports;

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        isDeleted = isDeleted;
    }
}
