package com.hacof.submission.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SubmissionfileResponseDTO {
    private Long id;
    private Long submissionId;
    private Long roundId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String status;
    private String feedback;
    private Instant uploadedAt;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
