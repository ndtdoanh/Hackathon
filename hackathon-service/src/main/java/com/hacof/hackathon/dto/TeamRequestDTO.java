package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.entity.AuditCreatedBase;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamRequestDTO extends AuditCreatedBase {
    String id;
    String hackathonId;
    Status status;
    LocalDateTime confirmationDeadline;
    String note;
    String reviewedById;
    List<TeamRequestMemberDTO> teamRequestMembers;
}
