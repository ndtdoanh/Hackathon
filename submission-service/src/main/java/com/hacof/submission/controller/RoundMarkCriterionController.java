package com.hacof.submission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hacof.submission.dto.request.RoundMarkCriterionRequestDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.response.CommonResponse;
import com.hacof.submission.service.RoundMarkCriterionService;

@RestController
@RequestMapping("/api/v1/roundmarkcriteria")
public class RoundMarkCriterionController {

    @Autowired
    private RoundMarkCriterionService service;

    @GetMapping
    public ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> getAll() {
        CommonResponse<List<RoundMarkCriterionResponseDTO>> response = new CommonResponse<>();
        try {
            List<RoundMarkCriterionResponseDTO> data = service.getAll();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all round mark criteria successfully!");
            response.setData(data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> getById(@PathVariable Long id) {
        CommonResponse<RoundMarkCriterionResponseDTO> response = new CommonResponse<>();
        try {
            RoundMarkCriterionResponseDTO criterion = service.getById(id)
                    .orElseThrow(() -> new IllegalArgumentException("RoundMarkCriterion not found with id " + id));
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched round mark criterion by ID successfully!");
            response.setData(criterion);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROUND_MARK_CRITERIA')")
    public ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> create(
            @RequestBody RoundMarkCriterionRequestDTO criterion) {
        CommonResponse<RoundMarkCriterionResponseDTO> response = new CommonResponse<>();
        try {
            RoundMarkCriterionResponseDTO created = service.create(criterion);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Round mark criterion created successfully!");
            response.setData(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
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
    @PreAuthorize("hasAuthority('UPDATE_ROUND_MARK_CRITERIA')")
    public ResponseEntity<CommonResponse<RoundMarkCriterionResponseDTO>> update(
            @PathVariable Long id, @RequestBody RoundMarkCriterionRequestDTO updatedCriterion) {
        CommonResponse<RoundMarkCriterionResponseDTO> response = new CommonResponse<>();
        try {
            RoundMarkCriterionResponseDTO updated = service.update(id, updatedCriterion);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Round mark criterion updated successfully!");
            response.setData(updated);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
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
    @PreAuthorize("hasAuthority('DELETE_ROUND_MARK_CRITERIA')")
    public ResponseEntity<CommonResponse<Void>> delete(@PathVariable Long id) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            service.delete(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Round mark criterion deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/by-round/{roundId}")
    public ResponseEntity<CommonResponse<List<RoundMarkCriterionResponseDTO>>> getByRoundId(
            @PathVariable Long roundId) {
        CommonResponse<List<RoundMarkCriterionResponseDTO>> response = new CommonResponse<>();
        try {
            List<RoundMarkCriterionResponseDTO> data = service.getByRoundId(roundId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched round mark criteria by roundId successfully!");
            response.setData(data);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
