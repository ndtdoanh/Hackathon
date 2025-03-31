package com.hacof.hackathon.dto;

import com.hacof.hackathon.entity.AuditBase;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonResultDTO extends AuditBase {
    String id;
    HackathonDTO hackathon;
    String hackathonId;
    TeamDTO team;
    String teamId;
    int totalScore;
    int placement;
    String award;
}
