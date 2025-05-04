package com.hacof.hackathon.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmissionDTO {
    String id;
    String roundId;
    RoundDTO round;
    String teamId;
    TeamDTO team;
    String status;
    String submissionAt;
    String finalScore;
}
