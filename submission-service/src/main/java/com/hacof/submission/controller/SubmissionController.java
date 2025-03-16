package com.hacof.submission.controller;

import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.SubmissionResponseDTO;
import com.hacof.submission.response.CommonResponse;
import com.hacof.submission.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    /**
     * Endpoint to create a submission.
     */
    @PostMapping
    public ResponseEntity<CommonResponse<SubmissionResponseDTO>> createSubmission(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("roundId") Long roundId,
            @RequestParam("status") String status) {
        CommonResponse<SubmissionResponseDTO> response = new CommonResponse<>();
        try {
            SubmissionRequestDTO submissionRequestDTO = new SubmissionRequestDTO();
            submissionRequestDTO.setRoundId(roundId);
            submissionRequestDTO.setStatus(status);

            // Call the service to create the submission
            SubmissionResponseDTO createdSubmission = submissionService.createSubmission(submissionRequestDTO, files);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Submission created successfully!");
            response.setData(createdSubmission);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error uploading files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint to get a specific submission by ID.
     */
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

    /**
     * Endpoint to get all submissions.
     */
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

    /**
     * Endpoint to update an existing submission by ID.
     */
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

    /**
     * Endpoint to delete a submission by ID.
     */
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
