package com.hacof.submission.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hacof.submission.entity.Submission;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmissionResponseDTO {
    String id;
    RoundResponseDTO round;
    TeamResponseDTO team;
    String status;
    LocalDateTime submittedAt;
    List<FileUrlResponseDTO> fileUrls;
    List<JudgeSubmissionResponseDTO> judgeSubmissions;
    Double finalScore;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdByUserName;

    public SubmissionResponseDTO(Submission submission) {
        if (submission != null) {
            this.id = String.valueOf(submission.getId());
            this.round = submission.getRound() != null ? new RoundResponseDTO() : null;
            this.team = submission.getTeam() != null ? new TeamResponseDTO(submission.getTeam()) : null;
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
            this.createdAt = submission.getCreatedDate();
            this.updatedAt = submission.getLastModifiedDate();
            this.createdByUserName = submission.getCreatedBy() != null
                    ? submission.getCreatedBy().getUsername()
                    : "Unknown";
        }
    }
}
