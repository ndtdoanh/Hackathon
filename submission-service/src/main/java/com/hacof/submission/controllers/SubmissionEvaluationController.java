package com.hacof.submission.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.submission.dtos.request.SubmissionEvaluationRequestDTO;
import com.hacof.submission.dtos.response.SubmissionEvaluationResponseDTO;
import com.hacof.submission.responses.CommonResponse;
import com.hacof.submission.services.SubmissionEvaluationService;

@RestController
@RequestMapping("/api/v1/submissionevaluations")
public class SubmissionEvaluationController {
    @Autowired
    private SubmissionEvaluationService service;

    @GetMapping
    public ResponseEntity<CommonResponse<List<SubmissionEvaluationResponseDTO>>> getAllEvaluations() {
        CommonResponse<List<SubmissionEvaluationResponseDTO>> response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all submission evaluations successfully!");
            response.setData(service.getAllEvaluations());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<SubmissionEvaluationResponseDTO>> getEvaluationById(@PathVariable Long id) {
        CommonResponse<SubmissionEvaluationResponseDTO> response = new CommonResponse<>();
        try {
            SubmissionEvaluationResponseDTO evaluation = service.getEvaluationById(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submission evaluation successfully!");
            response.setData(evaluation);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Evaluation not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CommonResponse<SubmissionEvaluationResponseDTO>> createEvaluation(
            @RequestBody SubmissionEvaluationRequestDTO evaluationRequestDTO) {
        CommonResponse<SubmissionEvaluationResponseDTO> response = new CommonResponse<>();
        try {
            SubmissionEvaluationResponseDTO created = service.createEvaluation(evaluationRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Submission evaluation created successfully!");
            response.setData(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<SubmissionEvaluationResponseDTO>> updateEvaluation(
            @PathVariable Long id, @RequestBody SubmissionEvaluationRequestDTO evaluationRequestDTO) {
        CommonResponse<SubmissionEvaluationResponseDTO> response = new CommonResponse<>();
        try {
            SubmissionEvaluationResponseDTO updatedScore = service.updateEvaluation(id, evaluationRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Submission evaluation updated successfully!");
            response.setData(updatedScore);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteEvaluation(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        if (service.deleteEvaluation(id)) {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Submission evaluation deleted successfully!");
            return ResponseEntity.ok(response);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Submission evaluation not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
