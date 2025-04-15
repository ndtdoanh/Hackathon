package com.hacof.communication.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForumCategoryResponseDTO {
    String id;
    String name;
    String description;
    String section;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<ForumThreadResponseDTO> forumThreads;
}
