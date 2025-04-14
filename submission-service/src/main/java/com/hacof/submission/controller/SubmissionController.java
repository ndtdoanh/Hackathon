package com.hacof.submission.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hacof.submission.dto.request.SubmissionRequestDTO;
import com.hacof.submission.dto.response.SubmissionResponseDTO;
import com.hacof.submission.service.SubmissionService;
import com.hacof.submission.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    public void setCommonResponseFields(CommonResponse<SubmissionResponseDTO> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    private void setDefaultResponseFields(CommonResponse<?> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_SUBMISSION')")
    public ResponseEntity<CommonResponse<SubmissionResponseDTO>> createSubmission(
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "roundId", required = false) String roundIdStr,
            @RequestParam(value = "teamId", required = false) String teamIdStr,
            @RequestParam("status") String status) {

        CommonResponse<SubmissionResponseDTO> response = new CommonResponse<>();
        try {
            if (roundIdStr == null || teamIdStr == null || roundIdStr.isEmpty() || teamIdStr.isEmpty()) {
                throw new IllegalArgumentException("Round ID and Team ID must not be null or empty.");
            }

            SubmissionRequestDTO submissionRequestDTO = new SubmissionRequestDTO();
            submissionRequestDTO.setRoundId(roundIdStr);
            submissionRequestDTO.setTeamId(teamIdStr);
            submissionRequestDTO.setStatus(status);

            SubmissionResponseDTO createdSubmission = submissionService.createSubmission(submissionRequestDTO, files);

            setCommonResponseFields(response);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Submission created successfully!");
            response.setData(createdSubmission);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (IOException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Error uploading files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submission successfully!");
            response.setData(submission);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all submissions successfully!");
            response.setData(submissions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_SUBMISSION')")
    public ResponseEntity<CommonResponse<SubmissionResponseDTO>> updateSubmission(
            @PathVariable Long id,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestParam("roundId") Long roundId,
            @RequestParam("teamId") Long teamId,
            @RequestParam("status") String status) {

        CommonResponse<SubmissionResponseDTO> response = new CommonResponse<>();
        try {
            SubmissionRequestDTO submissionRequestDTO = new SubmissionRequestDTO();
            submissionRequestDTO.setRoundId(String.valueOf(roundId));
            submissionRequestDTO.setTeamId(String.valueOf(teamId));
            submissionRequestDTO.setStatus(status);

            SubmissionResponseDTO updatedSubmission =
                    submissionService.updateSubmission(id, submissionRequestDTO, files);

            setCommonResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Submission updated successfully!");
            response.setData(updatedSubmission);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_SUBMISSION')")
    public ResponseEntity<CommonResponse<Void>> deleteSubmission(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            submissionService.deleteSubmission(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Submission deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Submission not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/by-round-created")
    public ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> getSubmissionsByRoundAndCreatedBy(
            @RequestParam Long roundId, @RequestParam String createdByUsername) {
        CommonResponse<List<SubmissionResponseDTO>> response = new CommonResponse<>();
        try {
            List<SubmissionResponseDTO> list =
                    submissionService.getSubmissionsByRoundAndCreatedBy(roundId, createdByUsername);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submissions by round and creator successfully.");
            response.setData(list);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-team-round")
    public ResponseEntity<CommonResponse<List<SubmissionResponseDTO>>> getSubmissionsByTeamAndRound(
            @RequestParam Long teamId, @RequestParam Long roundId) {
        CommonResponse<List<SubmissionResponseDTO>> response = new CommonResponse<>();
        try {
            List<SubmissionResponseDTO> list = submissionService.getSubmissionsByTeamAndRound(teamId, roundId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submissions by team and round successfully.");
            response.setData(list);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
