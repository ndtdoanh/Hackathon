package com.hacof.submission.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.submission.dto.request.JudgeRoundRequestDTO;
import com.hacof.submission.dto.response.JudgeRoundResponseDTO;
import com.hacof.submission.service.JudgeRoundService;
import com.hacof.submission.util.CommonRequest;
import com.hacof.submission.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/judge-rounds")
public class JudgeRoundController {

    @Autowired
    private JudgeRoundService judgeRoundService;

    private void setCommonResponseFields(CommonResponse<?> response, CommonRequest<?> request) {
        response.setRequestId(
                request.getRequestId() != null
                        ? request.getRequestId()
                        : UUID.randomUUID().toString());
        response.setRequestDateTime(
                request.getRequestDateTime() != null ? request.getRequestDateTime() : LocalDateTime.now());
        response.setChannel(request.getChannel() != null ? request.getChannel() : "HACOF");
    }

    private void setDefaultResponseFields(CommonResponse<?> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_JUDGE_ROUND')")
    public ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> createJudgeRound(
            @RequestBody CommonRequest<JudgeRoundRequestDTO> request) {
        CommonResponse<JudgeRoundResponseDTO> response = new CommonResponse<>();
        try {
            JudgeRoundResponseDTO createdJudgeRound = judgeRoundService.createJudgeRound(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Judge Round created successfully");
            response.setData(createdJudgeRound);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_JUDGE_ROUND')")
    public ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> updateJudgeRound(
            @PathVariable Long id, @RequestBody CommonRequest<JudgeRoundRequestDTO> request) {
        CommonResponse<JudgeRoundResponseDTO> response = new CommonResponse<>();
        try {
            JudgeRoundResponseDTO updatedJudgeRound = judgeRoundService.updateJudgeRound(id, request.getData());
            if (updatedJudgeRound == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Judge Round not found");
                setDefaultResponseFields(response);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge Round updated successfully");
            response.setData(updatedJudgeRound);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_JUDGE_ROUND')")
    public ResponseEntity<CommonResponse<Boolean>> deleteJudgeRound(@PathVariable Long id) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        try {
            boolean deleted = judgeRoundService.deleteJudgeRound(id);
            setDefaultResponseFields(response);
            if (!deleted) {
                setDefaultResponseFields(response);
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Judge Round not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Judge Round deleted successfully");
            response.setData(true);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            if (judgeRound == null) {
                setDefaultResponseFields(response);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Judge Round not found, returning empty response");
                response.setData(null);
                return ResponseEntity.ok(response);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge Round fetched successfully");
            response.setData(judgeRound);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all JudgeRounds successfully!");
            response.setData(judgeRounds);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/judge/{judgeId}")
    @PreAuthorize("hasAuthority('UPDATE_JUDGE_ROUND_BY_JUDGE_ID')")
    public ResponseEntity<CommonResponse<JudgeRoundResponseDTO>> updateJudgeRoundByJudgeId(
            @PathVariable Long judgeId, @RequestBody CommonRequest<JudgeRoundRequestDTO> request) {
        CommonResponse<JudgeRoundResponseDTO> response = new CommonResponse<>();
        try {
            JudgeRoundResponseDTO updatedDetail =
                    judgeRoundService.updateJudgeRoundByJudgeId(judgeId, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("JudgeRound updated successfully");
            response.setData(updatedDetail);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            if (judgeRounds.isEmpty()) {
                setDefaultResponseFields(response);
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("No JudgeRounds found for the given roundId, returning empty list");
                response.setData(new ArrayList<>());
                return ResponseEntity.ok(response);
            }
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("JudgeRounds fetched successfully");
            response.setData(judgeRounds);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/by-judge-round")
    @PreAuthorize("hasAuthority('DELETE_JUDGE_ROUND_BY_JUDGE_ID_AND_ROUND_ID')")
    public ResponseEntity<CommonResponse<Void>> deleteJudgeRoundByJudgeIdAndRoundId(
            @RequestParam Long judgeId, @RequestParam Long roundId) {
        CommonResponse<Void> response = new CommonResponse<>();
        try {
            judgeRoundService.deleteJudgeRoundByJudgeIdAndRoundId(judgeId, roundId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("JudgeRound deleted successfully!");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
