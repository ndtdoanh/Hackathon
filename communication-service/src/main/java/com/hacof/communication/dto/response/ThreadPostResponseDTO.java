package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

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
