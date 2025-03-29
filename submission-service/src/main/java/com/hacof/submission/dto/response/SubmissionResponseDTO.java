package com.hacof.submission.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.hacof.submission.entity.Submission;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionResponseDTO {
    private Long id;
    private RoundResponseDTO round;
    private String status;
    private LocalDateTime submittedAt;
    private List<FileUrlResponseDTO> fileUrls;
    private List<JudgeSubmissionResponseDTO> judgeSubmissions;
    private Integer finalScore;

    // Constructor to map from Submission entity
    public SubmissionResponseDTO(Submission submission) {
        if (submission != null) {
            this.id = submission.getId();
            this.round = submission.getRound() != null
                    ? new RoundResponseDTO()
                    : null; // ✅ Fix: Correctly map RoundResponseDTO
            this.status =
                    submission.getStatus() != null ? submission.getStatus().toString() : null;
            this.submittedAt = submission.getSubmittedAt();
            this.finalScore = submission.getFinalScore();
            if (submission.getFileUrls() != null) {
                this.fileUrls = submission.getFileUrls().stream()
                        .map(file -> new FileUrlResponseDTO(
                                file.getFileName(), file.getFileUrl(), file.getFileType(), file.getFileSize()))
                        .collect(Collectors.toList());
            }

            if (submission.getJudgeSubmissions() != null) {
                this.judgeSubmissions = submission.getJudgeSubmissions().stream()
                        .map(JudgeSubmissionResponseDTO::new)
                        .collect(Collectors.toList());
            }
        }
    }
}
