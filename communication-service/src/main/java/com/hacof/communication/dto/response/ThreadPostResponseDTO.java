package com.hacof.communication.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ThreadPostResponseDTO {

    private Long id;
    private Long forumThreadId;
    private String content;
    private boolean isDeleted;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
