package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumCategoryResponseDTO {
    private long id;
    private String name;
    private String description;
    private String section;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
