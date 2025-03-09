package com.hacof.submission.services;

import com.hacof.submission.dtos.request.SubmissionRequestDTO;
import com.hacof.submission.dtos.response.SubmissionResponseDTO;

import java.util.List;

public interface SubmissionService {
    SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionRequestDTO);
    SubmissionResponseDTO updateSubmission(Long id, SubmissionRequestDTO submissionRequestDTO);
    SubmissionResponseDTO getSubmissionById(Long id);
    List<SubmissionResponseDTO> getAllSubmissions();
    void deleteSubmission(Long id);
}
