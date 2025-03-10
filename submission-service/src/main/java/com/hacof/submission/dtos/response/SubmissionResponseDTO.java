package com.hacof.submission.dtos.response;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionResponseDTO {
    private Long id;
    private Long teamId;
    private Long hackathonId;
    private String submissionUrl;
    private Long roundId;
    private String status;
    private String feedback;
    private Instant submittedAt;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
