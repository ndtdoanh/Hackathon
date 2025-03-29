package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadPostLikeResponseDTO {

    private long id;
    private ThreadPostResponseDTO threadPost;  // Include the full ThreadPost object
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
