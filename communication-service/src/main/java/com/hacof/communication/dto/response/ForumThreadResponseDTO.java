package com.hacof.communication.dto.response;

import java.util.List;

import com.hacof.communication.entity.ForumThread;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumThreadResponseDTO {

    private Long id;
    private String title;
    private ForumCategoryResponseDTO forumCategory; // Include ForumCategory DTO
    private boolean isLocked;
    private boolean isPinned;
    private String createdBy;
    private String createdDate;
    private String lastModifiedDate;
    private List<ThreadPostResponseDTO> threadPosts; // Include List of ThreadPost DTOs



}
