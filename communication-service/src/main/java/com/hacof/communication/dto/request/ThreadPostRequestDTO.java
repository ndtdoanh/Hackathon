package com.hacof.communication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadPostRequestDTO {
    private Long forumThreadId;
    private String content;
}
