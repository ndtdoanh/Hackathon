package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class InviteRequestDTO {
    private Long teamId;
    private Long userId;
    private Long inviterId;
}
