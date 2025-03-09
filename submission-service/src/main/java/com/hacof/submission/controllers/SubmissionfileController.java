package com.hacof.submission.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.submission.dtos.response.SubmissionfileResponseDTO;
import com.hacof.submission.responses.CommonResponse;
import com.hacof.submission.services.SubmissionfileService;

@RestController
@RequestMapping("/api/v1/submissionfiles")
public class SubmissionfileController {

    @Autowired
    private SubmissionfileService submissionfileService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse<SubmissionfileResponseDTO>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("submissionId") Long submissionId,
            @RequestParam("roundId") Long roundId) {

        CommonResponse<SubmissionfileResponseDTO> response = new CommonResponse<>();

        try {
            // Gọi service để upload file
            SubmissionfileResponseDTO fileResponse = submissionfileService.uploadFile(file, submissionId, roundId);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("File uploaded successfully!");
            response.setData(fileResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
