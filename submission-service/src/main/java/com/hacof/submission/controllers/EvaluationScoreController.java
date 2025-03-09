package com.hacof.submission.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.submission.dtos.request.EvaluationScoreRequestDTO;
import com.hacof.submission.dtos.response.EvaluationScoreResponseDTO;
import com.hacof.submission.responses.CommonResponse;
import com.hacof.submission.services.EvaluationScoreService;

@RestController
@RequestMapping("/api/v1/evaluationscores")
public class EvaluationScoreController {

    @Autowired
    private EvaluationScoreService service;

    @GetMapping
    public ResponseEntity<CommonResponse<List<EvaluationScoreResponseDTO>>> getAllScores() {
        CommonResponse<List<EvaluationScoreResponseDTO>> response = new CommonResponse<>();
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all evaluation scores successfully!");
            response.setData(service.getAllScores());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<EvaluationScoreResponseDTO>> getScoreById(@PathVariable Integer id) {
        CommonResponse<EvaluationScoreResponseDTO> response = new CommonResponse<>();
        try {
            EvaluationScoreResponseDTO score = service.getScoreById(id);
            if (score == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("EvaluationScore not found with id " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched evaluation score by ID successfully!");
            response.setData(score);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CommonResponse<EvaluationScoreResponseDTO>> createScore(
            @RequestBody EvaluationScoreRequestDTO scoreRequestDTO) {
        CommonResponse<EvaluationScoreResponseDTO> response = new CommonResponse<>();
        try {
            EvaluationScoreResponseDTO created = service.createScore(scoreRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Evaluation score created successfully!");
            response.setData(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<EvaluationScoreResponseDTO>> updateScore(
            @PathVariable Integer id, @RequestBody EvaluationScoreRequestDTO scoreDetails) {
        CommonResponse<EvaluationScoreResponseDTO> response = new CommonResponse<>();
        try {
            EvaluationScoreResponseDTO updatedScore = service.updateScore(id, scoreDetails);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Evaluation score updated successfully!");
            response.setData(updatedScore);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteScore(@PathVariable Integer id) {
        CommonResponse<Void> response = new CommonResponse<>();
        if (service.deleteScore(id)) {
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Evaluation score deleted successfully!");
            return ResponseEntity.ok(response);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Evaluation score not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
