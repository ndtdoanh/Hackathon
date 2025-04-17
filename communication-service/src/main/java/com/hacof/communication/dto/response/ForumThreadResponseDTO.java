package com.hacof.communication.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForumThreadResponseDTO {

    String id;
    String title;
    ForumCategoryResponseDTO forumCategory; // Include ForumCategory DTO
    boolean isLocked;
    boolean isPinned;
    String createdByUserName;
    String createdAt;
    String updatedAt;
    List<ThreadPostResponseDTO> threadPosts; // Include List of ThreadPost DTOs
}
