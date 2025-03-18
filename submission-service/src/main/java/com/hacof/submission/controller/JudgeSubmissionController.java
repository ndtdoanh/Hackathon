package com.hacof.submission.controller;

import com.hacof.submission.dto.request.AssignJudgeRequest;
import com.hacof.submission.dto.request.UpdateScoreRequest;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
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

    @PostMapping("/assign")
    public ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> assignJudgeToSubmission(@RequestBody AssignJudgeRequest assignJudgeRequest) {
        CommonResponse<JudgeSubmissionResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionResponseDTO responseDTO = judgeSubmissionService.assignJudgeToSubmission(assignJudgeRequest);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge assigned to submission successfully");
            response.setData(responseDTO);
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

    @PutMapping("/update-score")
    public ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> updateScoreAndNoteForSubmission(@RequestBody UpdateScoreRequest updateScoreRequest) {
        CommonResponse<JudgeSubmissionResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionResponseDTO responseDTO = judgeSubmissionService.updateScoreAndNoteForSubmission(updateScoreRequest);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Score and note updated successfully");
            response.setData(responseDTO);
            return ResponseEntity.ok(response);
        } catch  (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<CommonResponse<JudgeSubmissionResponseDTO>> getJudgeSubmissionBySubmissionId(@PathVariable Long submissionId) {
        CommonResponse<JudgeSubmissionResponseDTO> response = new CommonResponse<>();
        try {
            JudgeSubmissionResponseDTO responseDTO = judgeSubmissionService.getJudgeSubmissionBySubmissionId(submissionId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Judge submission fetched successfully");
            response.setData(responseDTO);
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
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{submissionId}")
    public ResponseEntity<CommonResponse<Boolean>> deleteJudgeSubmission(@PathVariable Long submissionId) {
        CommonResponse<Boolean> response = new CommonResponse<>();
        try {
            boolean deleted = judgeSubmissionService.deleteJudgeSubmission(submissionId);
            if (deleted) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.setMessage("Judge submission deleted successfully");
                response.setData(true);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("Judge submission not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
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

    @GetMapping("/by-judge/{judgeId}")
    public ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> getSubmissionsByJudgeId(@PathVariable Long judgeId) {
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
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-round/{roundId}")
    public ResponseEntity<CommonResponse<List<JudgeSubmissionResponseDTO>>> getSubmissionsByRoundId(@PathVariable Long roundId) {
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
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
