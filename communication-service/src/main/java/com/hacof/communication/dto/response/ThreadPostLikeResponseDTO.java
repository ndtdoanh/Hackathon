package com.hacof.communication.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ThreadPostLikeResponseDTO {

    private long id;
    private long threadPostId;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;


}
