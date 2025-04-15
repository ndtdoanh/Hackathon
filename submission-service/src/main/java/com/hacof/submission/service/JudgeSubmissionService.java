package com.hacof.submission.service;

import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;

import java.util.List;

public interface JudgeSubmissionService {
    JudgeSubmissionResponseDTO createJudgeSubmission(JudgeSubmissionRequestDTO requestDTO);

    JudgeSubmissionResponseDTO getJudgeSubmissionById(Long id);

    List<JudgeSubmissionResponseDTO> getAllJudgeSubmissions();

    JudgeSubmissionResponseDTO updateJudgeSubmission(Long id, JudgeSubmissionRequestDTO requestDTO);

    void deleteJudgeSubmission(Long id);

    List<JudgeSubmissionResponseDTO> getSubmissionsByJudgeId(Long judgeId);

    List<JudgeSubmissionResponseDTO> getSubmissionsByRoundId(Long roundId);
}
