package com.hacof.communication.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForumThreadRequestDTO {

    String title;
    String forumCategoryId;
    boolean isLocked;
    boolean isPinned;

    public boolean getIsLocked() {
        return isLocked;
    }

    public boolean getIsPinned() {
        return isPinned;
    }
}
