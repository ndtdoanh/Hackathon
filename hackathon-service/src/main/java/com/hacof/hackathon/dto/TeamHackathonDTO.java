package com.hacof.hackathon.dto;

import com.hacof.hackathon.constant.TeamHackathonStatus;
import com.hacof.hackathon.entity.AuditBase;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamHackathonDTO extends AuditBase {
    String id;
    TeamDTO team;
    String teamId;
    HackathonDTO hackathon;
    String hackathonId;
    TeamHackathonStatus status;
}
