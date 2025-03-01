package com.hacof.communication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogcommentRequestDTO {
    private Long postId;
    private Long userId;
    private String comment;
}