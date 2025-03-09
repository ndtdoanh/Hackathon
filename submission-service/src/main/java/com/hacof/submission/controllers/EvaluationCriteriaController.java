package com.hacof.submission.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.submission.dtos.request.EvaluationCriteriaRequestDTO;
import com.hacof.submission.dtos.response.EvaluationCriteriaResponseDTO;
import com.hacof.submission.responses.CommonResponse;
import com.hacof.submission.services.EvaluationCriteriaService;

@RestController
@RequestMapping("/api/v1/evaluationcriteria")
public class EvaluationCriteriaController {

    @Autowired
    private EvaluationCriteriaService service;

    @GetMapping
    public ResponseEntity<CommonResponse<List<EvaluationCriteriaResponseDTO>>> getAll() {
        CommonResponse<List<EvaluationCriteriaResponseDTO>> response = new CommonResponse<>();
        try {
            List<EvaluationCriteriaResponseDTO> data = service.getAll();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all evaluation criteria successfully!");
            response.setData(data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<EvaluationCriteriaResponseDTO>> getById(@PathVariable Integer id) {
        CommonResponse<EvaluationCriteriaResponseDTO> response = new CommonResponse<>();
        try {
            EvaluationCriteriaResponseDTO criteria = service.getById(id)
                    .orElseThrow(() -> new RuntimeException("EvaluationCriteria not found with id " + id));
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched evaluation criteria by ID successfully!");
            response.setData(criteria);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CommonResponse<EvaluationCriteriaResponseDTO>> create(
            @RequestBody EvaluationCriteriaRequestDTO criteria) {
        CommonResponse<EvaluationCriteriaResponseDTO> response = new CommonResponse<>();
        try {
            EvaluationCriteriaResponseDTO created = service.create(criteria);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Evaluation criteria created successfully!");
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
    public ResponseEntity<CommonResponse<EvaluationCriteriaResponseDTO>> update(
            @PathVariable Integer id, @RequestBody EvaluationCriteriaRequestDTO updatedCriteria) {
        CommonResponse<EvaluationCriteriaResponseDTO> response = new CommonResponse<>();
        try {
            EvaluationCriteriaResponseDTO updated = service.update(id, updatedCriteria);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Evaluation criteria updated successfully!");
            response.setData(updated);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Integer id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            service.delete(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Evaluation criteria deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Evaluation criteria with id " + id + " not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
