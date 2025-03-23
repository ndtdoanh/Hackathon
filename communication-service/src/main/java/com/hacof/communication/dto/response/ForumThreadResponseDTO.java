package com.hacof.communication.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumThreadResponseDTO {

    private Long id;
    private String title;
    private String forumCategoryName;
    private boolean isLocked;
    private boolean isPinned;
    private String createdBy;
    private String createdDate;
    private String lastModifiedDate;
}
