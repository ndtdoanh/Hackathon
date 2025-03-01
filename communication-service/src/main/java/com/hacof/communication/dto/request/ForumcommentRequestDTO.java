package com.hacof.communication.dto.request;

import com.hacof.communication.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForumcommentRequestDTO {
    private Long threadId;
    private Long userId;
    private String comment;
    private Status status = Status.ACTIVE;
}

