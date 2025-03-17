package com.hacof.communication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumThreadRequestDTO {

    private String title;
    private Long forumCategoryId;
    private boolean isLocked;
    private boolean isPinned;
}