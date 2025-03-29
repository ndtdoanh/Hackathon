package com.hacof.submission.dto.response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.hacof.submission.entity.JudgeSubmission;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JudgeSubmissionResponseDTO {
    private Long id;
    private UserResponse judge;
    private SubmissionResponseDTO submission;
    private int score;
    private String note;
    private String createdDate;
    private String lastModifiedDate;
    private Set<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails;

    // Constructor that takes a JudgeSubmission entity
    public JudgeSubmissionResponseDTO(JudgeSubmission entity) {
        this.id = entity.getId();
        this.judge = entity.getJudge() != null ? new UserResponse(entity.getJudge()) : null;
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdDate =
                entity.getCreatedDate() != null ? entity.getCreatedDate().toString() : null;
        this.lastModifiedDate = entity.getLastModifiedDate() != null
                ? entity.getLastModifiedDate().toString()
                : null;

        this.judgeSubmissionDetails = entity.getJudgeSubmissionDetails() != null
                ? entity.getJudgeSubmissionDetails().stream()
                        .map(JudgeSubmissionDetailResponseDTO::new)
                        .collect(Collectors.toSet())
                : new HashSet<>();

        if (entity.getSubmission() != null) {
            SubmissionResponseDTO submissionResponseDTO = new SubmissionResponseDTO();
            submissionResponseDTO.setId(entity.getSubmission().getId());

            // ✅ Fix: Set đúng kiểu dữ liệu RoundResponseDTO
            if (entity.getSubmission().getRound() != null) {
                submissionResponseDTO.setRound(RoundResponseDTO.builder()
                        .id(entity.getSubmission().getRound().getId())
                        .roundTitle(entity.getSubmission().getRound().getRoundTitle())
                        .startTime(entity.getSubmission().getRound().getStartTime())
                        .endTime(entity.getSubmission().getRound().getEndTime())
                        .status(
                                entity.getSubmission().getRound().getStatus() != null
                                        ? entity.getSubmission()
                                                .getRound()
                                                .getStatus()
                                                .name()
                                        : "UNKNOWN")
                        .build());
            }

            submissionResponseDTO.setStatus(
                    entity.getSubmission().getStatus() != null
                            ? entity.getSubmission().getStatus().toString()
                            : "UNKNOWN");
            submissionResponseDTO.setSubmittedAt(entity.getSubmission().getSubmittedAt());

            if (entity.getSubmission().getFileUrls() != null) {
                List<FileUrlResponseDTO> fileUrls = entity.getSubmission().getFileUrls().stream()
                        .map(fileUrl -> new FileUrlResponseDTO(
                                fileUrl.getFileName(),
                                fileUrl.getFileUrl(),
                                fileUrl.getFileType(),
                                fileUrl.getFileSize()))
                        .collect(Collectors.toList());
                submissionResponseDTO.setFileUrls(fileUrls);
            }

            this.submission = submissionResponseDTO;
        }
    }
}
