package com.hacof.submission.service;

import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.SubmissionResponseDTO;
import com.hacof.submission.entity.Submission;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubmissionService {

    SubmissionResponseDTO createSubmission(SubmissionRequestDTO submissionDTO, List<MultipartFile> files) throws IOException;

    SubmissionResponseDTO getSubmissionById(Long id);

    List<SubmissionResponseDTO> getAllSubmissions();

    SubmissionResponseDTO updateSubmission(Long id, SubmissionRequestDTO submissionDTO);

    boolean deleteSubmission(Long id);
}
