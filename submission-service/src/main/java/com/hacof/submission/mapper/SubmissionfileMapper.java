package com.hacof.submission.mapper;

import com.hacof.submission.dtos.request.SubmissionfileRequestDTO;
import com.hacof.submission.dtos.response.SubmissionfileResponseDTO;
import com.hacof.submission.entities.Submissionfile;
import com.hacof.submission.entities.Submission;
import com.hacof.submission.entities.Competitionround;
import com.hacof.submission.repositories.SubmissionRepository;
import com.hacof.submission.repositories.CompetitionroundRepository;
import com.hacof.submission.enums.FileType;
import com.hacof.submission.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class SubmissionfileMapper {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private CompetitionroundRepository competitionroundRepository;

    // Chuyển từ SubmissionfileRequestDTO thành Submissionfile entity
    public Submissionfile toEntity(SubmissionfileRequestDTO dto) {
        Submissionfile submissionfile = new Submissionfile();

        // Lấy Submission và Round từ repository
        Submission submission = submissionRepository.findById(dto.getSubmissionId())
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        Competitionround round = competitionroundRepository.findById(dto.getRoundId())
                .orElseThrow(() -> new RuntimeException("Competition round not found"));

        submissionfile.setSubmission(submission);
        submissionfile.setRound(round);
        submissionfile.setFileName(dto.getFileName());
        submissionfile.setFileUrl(dto.getFileUrl());
        submissionfile.setFileType(dto.getFileType()); // Lấy FileType từ DTO
        submissionfile.setFeedback(dto.getFeedback());

        // Default status to PENDING if it's null
        submissionfile.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.PENDING);
        submissionfile.setUploadedAt(Instant.now()); // Set current timestamp

        return submissionfile;
    }

    // Chuyển từ Submissionfile entity thành SubmissionfileResponseDTO
    public SubmissionfileResponseDTO toResponseDTO(Submissionfile entity) {
        SubmissionfileResponseDTO dto = new SubmissionfileResponseDTO();
        dto.setId(entity.getId());
        dto.setSubmissionId(entity.getSubmission().getId());
        dto.setRoundId(entity.getRound().getId());
        dto.setFileName(entity.getFileName());
        dto.setFileUrl(entity.getFileUrl());
        dto.setFileType(entity.getFileType().toString()); // Convert FileType enum to String
        dto.setStatus(entity.getStatus().toString()); // Convert Status enum to String
        dto.setFeedback(entity.getFeedback());
        dto.setUploadedAt(entity.getUploadedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());

        return dto;
    }

    // Cập nhật Submissionfile từ SubmissionfileRequestDTO
    public void updateEntityFromDTO(SubmissionfileRequestDTO dto, Submissionfile entity) {
        entity.setFileName(dto.getFileName());
        entity.setFileUrl(dto.getFileUrl());
        entity.setFileType(dto.getFileType());
        entity.setFeedback(dto.getFeedback());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.PENDING); // Ensure status is set
        entity.setUploadedAt(Instant.now()); // Update uploadedAt on modification
    }
}
