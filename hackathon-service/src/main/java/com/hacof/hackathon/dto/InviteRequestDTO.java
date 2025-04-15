package com.hacof.hackathon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteRequestDTO {
    private Long teamId;
    private Long userId;
    private Long inviterId;
}
