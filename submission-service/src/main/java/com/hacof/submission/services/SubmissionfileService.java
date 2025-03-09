package com.hacof.submission.services;

import com.hacof.submission.dtos.request.SubmissionfileRequestDTO;
import com.hacof.submission.dtos.response.SubmissionfileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SubmissionfileService {
    SubmissionfileResponseDTO uploadFile(MultipartFile file, Long submissionId, Long roundId) throws IOException;
}
