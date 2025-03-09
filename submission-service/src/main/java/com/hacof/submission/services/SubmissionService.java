package com.hacof.submission.services;

import java.util.List;

import com.hacof.submission.dtos.request.SubmissionRequestDTO;
import com.hacof.submission.dtos.response.SubmissionResponseDTO;

public interface SubmissionService {
    SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionRequestDTO);

    SubmissionResponseDTO updateSubmission(Long id, SubmissionRequestDTO submissionRequestDTO);

    SubmissionResponseDTO getSubmissionById(Long id);

    List<SubmissionResponseDTO> getAllSubmissions();

    void deleteSubmission(Long id);
}
