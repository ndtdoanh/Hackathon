package com.hacof.submission.dto.response;

import com.hacof.submission.entity.JudgeSubmission;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class JudgeSubmissionResponseDTO {
    private Long id;
    private Long judgeId;
    private Long submissionId;
    private int score;
    private String note;
    private String createdDate;
    private String lastModifiedDate;
    private Set<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails;

    // Constructor that takes a JudgeSubmission entity
    public JudgeSubmissionResponseDTO(JudgeSubmission entity) {
        this.id = entity.getId();
        this.judgeId = entity.getJudge().getId();
        this.submissionId = entity.getSubmission().getId();
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate().toString(); // Convert LocalDateTime to String
        this.lastModifiedDate = entity.getLastModifiedDate().toString(); // Convert LocalDateTime to String
        // Convert judgeSubmissionDetails to a Set of JudgeSubmissionDetailResponseDTO
        this.judgeSubmissionDetails = entity.getJudgeSubmissionDetails() != null
                ? entity.getJudgeSubmissionDetails().stream()
                .map(detail -> new JudgeSubmissionDetailResponseDTO(detail)) // Assuming you have a constructor in DTO
                .collect(Collectors.toSet()) // Collect into Set
                : new HashSet<>();
    }
}
