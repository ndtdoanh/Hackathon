package com.hacof.hackathon.dto;

import lombok.Data;

@Data
public class InviteRequest {
    private Long teamId;
    private Long userId;
}
