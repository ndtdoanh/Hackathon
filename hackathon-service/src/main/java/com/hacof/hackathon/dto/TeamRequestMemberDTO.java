package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.TeamRequestMemberStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestMemberDTO {
    String id;
    String teamRequestId;
    UserDTO user;
    String userId;
    TeamRequestMemberStatus status;
    LocalDateTime respondedAt;

    // audit fields
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
