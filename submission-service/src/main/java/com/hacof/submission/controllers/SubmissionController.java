package com.hacof.submission.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.submission.dtos.request.SubmissionRequestDTO;
import com.hacof.submission.dtos.response.SubmissionResponseDTO;
import com.hacof.submission.responses.CommonResponse;
import com.hacof.submission.services.SubmissionService;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<CommonResponse<SubmissionResponseDTO>> createSubmission(
            @RequestBody SubmissionRequestDTO submissionRequestDTO) {
        CommonResponse<SubmissionResponseDTO> response = new CommonResponse<>();
        try {
            SubmissionResponseDTO createdSubmission = submissionService.createSubmission(submissionRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Submission created successfully!");
            response.setData(createdSubmission);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<SubmissionResponseDTO>> getSubmissionById(@PathVariable Long id) {
        CommonResponse<SubmissionResponseDTO> response = new CommonResponse<>();
        try {
            SubmissionResponseDTO submission = submissionService.getSubmissionById(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submission successfully!");
            response.setData(submission);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Submission not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> getAllSubmissions() {
        CommonResponse<List<SubmissionResponseDTO>> response = new CommonResponse<>();
        try {
            List<SubmissionResponseDTO> submissions = submissionService.getAllSubmissions();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all submissions successfully!");
            response.setData(submissions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<SubmissionResponseDTO>> updateSubmission(
            @PathVariable Long id, @RequestBody SubmissionRequestDTO submissionRequestDTO) {
        CommonResponse<SubmissionResponseDTO> response = new CommonResponse<>();
        try {
            SubmissionResponseDTO updatedSubmission = submissionService.updateSubmission(id, submissionRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Submission updated successfully!");
            response.setData(updatedSubmission);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Submission not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteSubmission(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            submissionService.deleteSubmission(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Submission deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Submission not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
