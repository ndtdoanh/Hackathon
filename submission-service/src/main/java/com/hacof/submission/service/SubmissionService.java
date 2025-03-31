package com.hacof.submission.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.SubmissionResponseDTO;

public interface SubmissionService {

    SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionDTO, List<MultipartFile> files)
            throws IOException;

    SubmissionResponseDTO getSubmissionById(Long id);

    List<SubmissionResponseDTO> getAllSubmissions();

    SubmissionResponseDTO updateSubmission(Long id, SubmissionRequestDTO submissionDTO, List<MultipartFile> files) throws IOException;

    boolean deleteSubmission(Long id);

    List<SubmissionResponseDTO> getSubmissionsByRoundAndCreatedBy(Long roundId, String createdByUsername);

}
