package com.hacof.submission.controller;


import com.hacof.submission.dto.request.AssignJudgeRequest;
import com.hacof.submission.dto.request.UpdateScoreRequest;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.service.JudgeSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/judge-submissions")
public class JudgeSubmissionController {

    @Autowired
    private JudgeSubmissionService judgeSubmissionService;

    @PostMapping("/assign")
    public ResponseEntity<JudgeSubmissionResponseDTO> assignJudgeToSubmission(@RequestBody AssignJudgeRequest assignJudgeDTO) {
        JudgeSubmissionResponseDTO responseDTO = judgeSubmissionService.assignJudgeToSubmission(assignJudgeDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/update-score")
    public ResponseEntity<JudgeSubmissionResponseDTO> updateScoreAndNoteForSubmission(@RequestBody UpdateScoreRequest updateScoreDTO) {
        JudgeSubmissionResponseDTO responseDTO = judgeSubmissionService.updateScoreAndNoteForSubmission(updateScoreDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
