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
public class ForumthreadRequestDTO {
    private Long hackathonId;
    private String title;

    private Status status = Status.ACTIVE;
}
