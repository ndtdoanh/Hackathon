package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestMemberResponseDTO {
    private String requestId;
    private String userId;
    private TeamRequestMemberStatus status;
    private String note;
}
