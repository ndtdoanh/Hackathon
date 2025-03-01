package com.hacof.communication.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogcommentResponseDTO {
    private Long id;
    private Long postId;
    private Long userId;
    private String comment;
    private String status;
    private String createdBy;
    private String updatedBy;
    private String createdAt;
    private String updatedAt;
}