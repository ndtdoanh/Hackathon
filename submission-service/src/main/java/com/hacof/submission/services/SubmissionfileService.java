package com.hacof.submission.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hacof.submission.dtos.response.SubmissionfileResponseDTO;

public interface SubmissionfileService {
    SubmissionfileResponseDTO uploadFile(MultipartFile file, Long submissionId, Long roundId) throws IOException;
}
