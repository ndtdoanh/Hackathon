package com.hacof.submission.service;

import java.util.List;

import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;

public interface JudgeSubmissionService {
    JudgeSubmissionResponseDTO createJudgeSubmission(JudgeSubmissionRequestDTO requestDTO);

    JudgeSubmissionResponseDTO getJudgeSubmissionById(Long id);

    List<JudgeSubmissionResponseDTO> getAllJudgeSubmissions();

    JudgeSubmissionResponseDTO updateJudgeSubmission(Long id, JudgeSubmissionRequestDTO requestDTO);

    void deleteJudgeSubmission(Long id);

    List<JudgeSubmissionResponseDTO> getSubmissionsByJudgeId(Long judgeId);

    List<JudgeSubmissionResponseDTO> getSubmissionsByRoundId(Long roundId);
}
