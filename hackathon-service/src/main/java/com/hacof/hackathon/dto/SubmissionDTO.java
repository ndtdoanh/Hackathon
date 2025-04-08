package com.hacof.hackathon.dto;

import lombok.*;
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
    // List<FileUrlDTO> submissionFiles;
    // List<JudgeSubmissionDTO> judgeSubmissions;
    String status;
    String submissionAt;
    String finalScore;
}
