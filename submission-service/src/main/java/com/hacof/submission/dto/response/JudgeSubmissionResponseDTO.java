package com.hacof.submission.dto.response;

import com.hacof.submission.entity.JudgeSubmission;
import lombok.Data;

@Data
public class JudgeSubmissionResponseDTO {
    private Long id;
    private Long judgeId;
    private Long submissionId;
    private int score;
    private String note;
    private String createdDate;  // Include created date
    private String lastModifiedDate;  // Include last modified date

    // Constructor that takes a JudgeSubmission entity
    public JudgeSubmissionResponseDTO(JudgeSubmission entity) {
        this.id = entity.getId();
        this.judgeId = entity.getJudge().getId();
        this.submissionId = entity.getSubmission().getId();
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate().toString();  // Convert LocalDateTime to String
        this.lastModifiedDate = entity.getLastModifiedDate().toString();  // Convert LocalDateTime to String
    }
}
