package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;

import lombok.*;
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
