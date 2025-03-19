package com.hacof.submission.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.hacof.submission.constant.Status;
import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.FileUrlResponseDTO;
import com.hacof.submission.dto.response.SubmissionResponseDTO;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.Submission;
import com.hacof.submission.repository.RoundRepository;

public class SubmissionMapper {

    // Modified to accept RoundRepository as a parameter
    public static Submission toEntity(SubmissionRequestDTO dto, RoundRepository roundRepository) {
        Submission submission = new Submission();

        // Fetch the round by ID from the database
        Round round = roundRepository
                .findById(dto.getRoundId())
                .orElseThrow(() -> new IllegalArgumentException("Round not found with ID " + dto.getRoundId()));

        submission.setRound(round); // Set the fetched round
        submission.setStatus(Status.valueOf(dto.getStatus().toUpperCase())); // Assuming Status is an enum
        return submission;
    }

    public static SubmissionResponseDTO toResponseDTO(Submission submission) {
        SubmissionResponseDTO responseDTO = new SubmissionResponseDTO();
        responseDTO.setId(submission.getId());
        responseDTO.setRoundId(submission.getRound().getId()); // Assuming round has an ID
        responseDTO.setStatus(submission.getStatus().toString());
        responseDTO.setSubmittedAt(submission.getSubmittedAt());

        // Map FileUrls
        List<FileUrlResponseDTO> fileUrls = submission.getFileUrls().stream()
                .map(fileUrl -> {
                    FileUrlResponseDTO fileResponse = new FileUrlResponseDTO();
                    fileResponse.setFileName(fileUrl.getFileName());
                    fileResponse.setFileUrl(fileUrl.getFileUrl());
                    fileResponse.setFileType(fileUrl.getFileType());
                    fileResponse.setFileSize(fileUrl.getFileSize());
                    return fileResponse;
                })
                .collect(Collectors.toList());

        responseDTO.setFileUrls(fileUrls);
        return responseDTO;
    }
}
