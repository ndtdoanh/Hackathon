package com.hacof.submission.service;

import com.hacof.submission.dto.request.AssignJudgeRequest;
import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.request.UpdateScoreRequest;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;

import java.util.List;

public interface JudgeSubmissionService {
    JudgeSubmissionResponseDTO assignJudgeToSubmission(AssignJudgeRequest assignJudgeDTO);
    JudgeSubmissionResponseDTO updateScoreAndNoteForSubmission(UpdateScoreRequest updateScoreDTO);
    JudgeSubmissionResponseDTO getJudgeSubmissionBySubmissionId(Long submissionId);
    List<JudgeSubmissionResponseDTO> getAllJudgeSubmissions();
    boolean deleteJudgeSubmission(Long submissionId);
    List<JudgeSubmissionResponseDTO> getSubmissionsByJudgeId(Long judgeId);
    List<JudgeSubmissionResponseDTO> getSubmissionsByRoundId(Long roundId);
    JudgeSubmissionResponseDTO getSubmissionScore(Long submissionId);
}
