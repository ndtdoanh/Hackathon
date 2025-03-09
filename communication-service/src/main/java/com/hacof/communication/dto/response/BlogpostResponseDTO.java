package com.hacof.communication.dto.response;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogpostResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String status;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
}
