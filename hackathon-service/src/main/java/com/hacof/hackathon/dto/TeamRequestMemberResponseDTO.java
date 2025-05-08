package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestMemberResponseDTO {
    String requestId;
    String userId;
    TeamRequestMemberStatus status;
    String note;
}
