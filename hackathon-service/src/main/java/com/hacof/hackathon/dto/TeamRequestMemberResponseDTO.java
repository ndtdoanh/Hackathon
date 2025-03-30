package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestMemberResponseDTO {
    private String requestId;
    private String userId;
    private TeamRequestMemberStatus status;
    private String note;
}
