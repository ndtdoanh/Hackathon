package com.hacof.submission.dto.response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.hacof.submission.entity.JudgeSubmission;

import lombok.Data;

@Data
public class JudgeSubmissionResponseDTO {
    private Long id;
    private Long judgeId;
    private SubmissionResponseDTO submission;
    private int score;
    private String note;
    private String createdDate;
    private String lastModifiedDate;
    private Set<JudgeSubmissionDetailResponseDTO> judgeSubmissionDetails;

    // Constructor that takes a JudgeSubmission entity
    public JudgeSubmissionResponseDTO(JudgeSubmission entity) {
        this.id = entity.getId();
        this.judgeId = entity.getJudge().getId();
        this.score = entity.getScore();
        this.note = entity.getNote();
        this.createdDate = entity.getCreatedDate().toString();
        this.lastModifiedDate = entity.getLastModifiedDate().toString();
        this.judgeSubmissionDetails = entity.getJudgeSubmissionDetails() != null
                ? entity.getJudgeSubmissionDetails().stream()
                        .map(detail -> new JudgeSubmissionDetailResponseDTO(detail))
                        .collect(Collectors.toSet())
                : new HashSet<>();

        if (entity.getSubmission() != null) {
            SubmissionResponseDTO submissionResponseDTO = new SubmissionResponseDTO();
            submissionResponseDTO.setId(entity.getSubmission().getId());
            submissionResponseDTO.setRoundId(entity.getSubmission().getRound().getId());
            submissionResponseDTO.setStatus(entity.getSubmission().getStatus().toString());
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
