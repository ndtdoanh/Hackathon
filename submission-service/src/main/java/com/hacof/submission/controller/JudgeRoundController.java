package com.hacof.submission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;
import com.hacof.submission.response.CommonResponse;
import com.hacof.submission.service.JudgeRoundService;

@RestController
@RequestMapping("/api/v1/judge-rounds")
public class JudgeRoundController {

    @Autowired
    private JudgeRoundService judgeRoundService;

    @PostMapping
    public ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> createJudgeRound(
            @RequestBody JudgeRoundRequestDTO dto) {
        CommonResponse<JudgeRoundResponseDTO> response = new CommonResponse<>();
        try {
            JudgeRoundResponseDTO createdJudgeRound = judgeRoundService.createJudgeRound(dto);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Judge Round created successfully");
            response.setData(createdJudgeRound);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> updateJudgeRound(
            @PathVariable Long id, @RequestBody JudgeRoundRequestDTO dto) {
        CommonResponse<JudgeRoundResponseDTO> response = new CommonResponse<>();
        try {
            JudgeRoundResponseDTO updatedJudgeRound = judgeRoundService.updateJudgeRound(id, dto);
            if (updatedJudgeRound == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Judge Round not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge Round updated successfully");
            response.setData(updatedJudgeRound);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Boolean>> deleteJudgeRound(@PathVariable Long id) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        try {
            boolean deleted = judgeRoundService.deleteJudgeRound(id);
            if (!deleted) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Judge Round not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Judge Round deleted successfully");
            response.setData(true); // Return true as the operation was successful
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> getJudgeRound(@PathVariable Long id) {
        CommonResponse<JudgeRoundResponseDTO> response = new CommonResponse<>();
        try {
            JudgeRoundResponseDTO judgeRound = judgeRoundService.getJudgeRound(id);
            if (judgeRound == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Judge Round not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge Round fetched successfully");
            response.setData(judgeRound);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<JudgeRoundResponseDTO>>> getAllJudgeRounds() {
        CommonResponse<List<JudgeRoundResponseDTO>> response = new CommonResponse<>();
        try {
            List<JudgeRoundResponseDTO> judgeRounds = judgeRoundService.getAllJudgeRounds();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all JudgeRounds successfully!");
            response.setData(judgeRounds);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/judge/{judgeId}")
    public ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> updateJudgeRoundByJudgeId(
            @PathVariable Long judgeId, @RequestBody JudgeRoundRequestDTO dto) {
        CommonResponse<JudgeRoundResponseDTO> response = new CommonResponse<>();
        try {
            JudgeRoundResponseDTO updatedDetail = judgeRoundService.updateJudgeRoundByJudgeId(judgeId, dto);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("JudgeRound updated successfully");
            response.setData(updatedDetail);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/round/{roundId}")
    public ResponseEntity<CommonResponse<List<JudgeRoundResponseDTO>>> getJudgeRoundsByRoundId(
            @PathVariable Long roundId) {
        CommonResponse<List<JudgeRoundResponseDTO>> response = new CommonResponse<>();
        try {
            List<JudgeRoundResponseDTO> judgeRounds = judgeRoundService.getJudgeRoundsByRoundId(roundId);
            if (judgeRounds.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("No JudgeRounds found for the given roundId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("JudgeRounds fetched successfully");
            response.setData(judgeRounds);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/by-judge-round")
    public ResponseEntity<CommonResponse<Void>> deleteJudgeRoundByJudgeIdAndRoundId(
            @RequestParam Long judgeId,
            @RequestParam Long roundId) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            judgeRoundService.deleteJudgeRoundByJudgeIdAndRoundId(judgeId, roundId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("JudgeRound deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
