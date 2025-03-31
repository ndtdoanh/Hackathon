package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.hackathon.constant.TeamRequestStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestDTO {
    String id;
    // HackathonDTO hackathon;
    String hackathonId;
    String name;
    TeamRequestStatus status;
    LocalDateTime confirmationDeadline;
    private String note;
    private String createdBy;
    private LocalDateTime createdDate;
    private String reviewedById;
    private List<TeamRequestMemberDTO> teamRequestMembers;
}
