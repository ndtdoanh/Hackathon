package com.hacof.communication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogpostRequestDTO {
    private String title;
    private String content;
    private Long authorId; // ID của tác giả
    private Long hackathonId; // ID của hackathon
}
