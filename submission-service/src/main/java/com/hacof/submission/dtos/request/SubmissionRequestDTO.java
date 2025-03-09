package com.hacof.submission.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SubmissionRequestDTO {
    private Long teamId;
    private Long hackathonId;
    private String submissionUrl;
    private Long roundId;
    private String feedback;
    private Instant submittedAt;
}
