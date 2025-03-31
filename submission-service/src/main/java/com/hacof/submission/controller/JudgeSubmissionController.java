package com.hacof.submission.controller;

import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.dto.response.SubmissionResponseDTO;
import com.hacof.submission.dto.response.UserResponse;
import com.hacof.submission.response.CommonResponse;
import com.hacof.submission.service.JudgeSubmissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/judge-submissions")
public class JudgeSubmissionController {

    @Autowired
    private JudgeSubmissionService judgeSubmissionService;

    @PostMapping
    public ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> createJudgeSubmission(
            @RequestBody JudgeSubmissionRequestDTO requestDTO) {
        CommonResponse<JudgeSubmissionResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionResponseDTO createdJudgeSubmission = judgeSubmissionService.createJudgeSubmission(requestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("JudgeSubmission created successfully!");
            response.setData(createdJudgeSubmission);
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

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> getJudgeSubmissionById(@PathVariable Long id) {
        CommonResponse<JudgeSubmissionResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionResponseDTO judgeSubmission = judgeSubmissionService.getJudgeSubmissionById(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge submission fetched successfully");
            response.setData(judgeSubmission);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> getAllJudgeSubmissions() {
        CommonResponse<List<JudgeSubmissionResponseDTO>> response = new CommonResponse<>();
        try {
            List<JudgeSubmissionResponseDTO> responseDTO = judgeSubmissionService.getAllJudgeSubmissions();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all judge submissions successfully");
            response.setData(responseDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> updateJudgeSubmission(
            @PathVariable Long id, @RequestBody JudgeSubmissionRequestDTO requestDTO) {
        CommonResponse<JudgeSubmissionResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionResponseDTO updatedJudgeSubmission = judgeSubmissionService.updateJudgeSubmission(id, requestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Score and note updated successfully");
            response.setData(updatedJudgeSubmission);
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
    public ResponseEntity<CommonResponse<Boolean>> deleteJudgeSubmission(@PathVariable Long id) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        try {
            judgeSubmissionService.deleteJudgeSubmission(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Judge submission deleted successfully");
            response.setData(true);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
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

    @GetMapping("/by-judge/{judgeId}")
    public ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> getSubmissionsByJudgeId(
            @PathVariable Long judgeId) {
        CommonResponse<List<JudgeSubmissionResponseDTO>> response = new CommonResponse<>();
        try {
            List<JudgeSubmissionResponseDTO> responseDTO = judgeSubmissionService.getSubmissionsByJudgeId(judgeId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submissions for the judge successfully");
            response.setData(responseDTO);
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

    @GetMapping("/by-round/{roundId}")
    public ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> getSubmissionsByRoundId(
            @PathVariable Long roundId) {
        CommonResponse<List<JudgeSubmissionResponseDTO>> response = new CommonResponse<>();
        try {
            List<JudgeSubmissionResponseDTO> responseDTO = judgeSubmissionService.getSubmissionsByRoundId(roundId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submissions for the round successfully");
            response.setData(responseDTO);
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
