package com.hacof.submission.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RestController;

import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.service.JudgeSubmissionService;
import com.hacof.submission.util.CommonRequest;
import com.hacof.submission.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/judge-submissions")
public class JudgeSubmissionController {

    @Autowired
    private JudgeSubmissionService judgeSubmissionService;

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
    @PreAuthorize("hasAuthority('CREATE_JUDGE_SUBMISSION')")
    public ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> createJudgeSubmission(
            @RequestBody CommonRequest<JudgeSubmissionRequestDTO> request) {
        CommonResponse<JudgeSubmissionResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionResponseDTO createdJudgeSubmission =
                    judgeSubmissionService.createJudgeSubmission(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("JudgeSubmission created successfully!");
            response.setData(createdJudgeSubmission);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge submission fetched successfully");
            response.setData(judgeSubmission);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched all judge submissions successfully");
            response.setData(responseDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_JUDGE_SUBMISSION')")
    public ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> updateJudgeSubmission(
            @PathVariable Long id, @RequestBody CommonRequest<JudgeSubmissionRequestDTO> request) {
        CommonResponse<JudgeSubmissionResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionResponseDTO updatedJudgeSubmission =
                    judgeSubmissionService.updateJudgeSubmission(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Score and note updated successfully");
            response.setData(updatedJudgeSubmission);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_JUDGE_SUBMISSION')")
    public ResponseEntity<CommonResponse<Boolean>> deleteJudgeSubmission(@PathVariable Long id) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        try {
            judgeSubmissionService.deleteJudgeSubmission(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Judge submission deleted successfully");
            response.setData(true);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submissions for the judge successfully");
            response.setData(responseDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Fetched submissions for the round successfully");
            response.setData(responseDTO);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
