package com.hacof.submission.service.impl;

import com.hacof.submission.dto.request.AssignJudgeRequest;
import com.hacof.submission.dto.request.UpdateScoreRequest;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.entity.JudgeSubmission;
import com.hacof.submission.entity.JudgeSubmissionDetail;
import com.hacof.submission.entity.Submission;
import com.hacof.submission.entity.User;
import com.hacof.submission.repository.JudgeSubmissionDetailRepository;
import com.hacof.submission.repository.JudgeSubmissionRepository;
import com.hacof.submission.repository.SubmissionRepository;
import com.hacof.submission.repository.UserRepository;
import com.hacof.submission.service.JudgeSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeSubmissionServiceImpl implements JudgeSubmissionService {

    @Autowired
    private JudgeSubmissionRepository judgeSubmissionRepository;

    @Autowired
    private JudgeSubmissionDetailRepository judgeSubmissionDetailRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public JudgeSubmissionResponseDTO assignJudgeToSubmission(AssignJudgeRequest assignJudgeDTO) {
        // Fetch submission and judge
        Submission submission = submissionRepository.findById(assignJudgeDTO.getSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("Submission not found"));
        User judge = userRepository.findById(assignJudgeDTO.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found"));

        // Create JudgeSubmission (without score and note)
        JudgeSubmission judgeSubmission = JudgeSubmission.builder()
                .judge(judge)
                .submission(submission)
                .score(0) // Default score
                .note("") // Default note
                .build();

        // Save JudgeSubmission
        judgeSubmission = judgeSubmissionRepository.save(judgeSubmission);

        // Return response DTO
        return new JudgeSubmissionResponseDTO(judgeSubmission); // Includes AuditBase fields
    }

    @Override
    public JudgeSubmissionResponseDTO updateScoreAndNoteForSubmission(UpdateScoreRequest updateScoreDTO) {
        // Fetch JudgeSubmission by ID
        JudgeSubmission judgeSubmission = judgeSubmissionRepository.findById(updateScoreDTO.getJudgeSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("JudgeSubmission not found"));

        // Update the note
        judgeSubmission.setNote(updateScoreDTO.getNote());

        // Recalculate the score based on JudgeSubmissionDetails
        recalculateTotalScore(judgeSubmission);

        // Save updated JudgeSubmission
        judgeSubmission = judgeSubmissionRepository.save(judgeSubmission);

        // Return response DTO
        return new JudgeSubmissionResponseDTO(judgeSubmission); // Includes AuditBase fields
    }

    private void recalculateTotalScore(JudgeSubmission judgeSubmission) {
        // Get all JudgeSubmissionDetail entries associated with this judgeSubmission
        Long judgeSubmissionId = judgeSubmission.getId();
        List<JudgeSubmissionDetail> judgeSubmissionDetails = judgeSubmissionDetailRepository.findByJudgeSubmissionId(judgeSubmissionId);

        // Calculate the total score by summing individual scores from JudgeSubmissionDetails
        int totalScore = judgeSubmissionDetails.stream()
                .mapToInt(JudgeSubmissionDetail::getScore) // Sum the scores of each JudgeSubmissionDetail
                .sum();

        // Update the total score in JudgeSubmission
        judgeSubmission.setScore(totalScore);
    }
}
