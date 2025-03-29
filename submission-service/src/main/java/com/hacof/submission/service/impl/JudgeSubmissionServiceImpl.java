package com.hacof.submission.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Fetch the submission and judge from the database
        Submission submission = submissionRepository
                .findById(assignJudgeDTO.getSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Submission not found with ID: " + assignJudgeDTO.getSubmissionId()));

        User judge = userRepository
                .findById(assignJudgeDTO.getJudgeId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Judge not found with ID: " + assignJudgeDTO.getJudgeId()));

        // Create a new JudgeSubmission entity (with default score and note)
        JudgeSubmission judgeSubmission = JudgeSubmission.builder()
                .judge(judge)
                .submission(submission)
                .score(0) // Default score
                .note("") // Default note
                .build();

        // Save the newly created JudgeSubmission entity
        judgeSubmission = judgeSubmissionRepository.save(judgeSubmission);

        // Return the created JudgeSubmission in a response DTO
        return new JudgeSubmissionResponseDTO(judgeSubmission);
    }

    @Override
    public JudgeSubmissionResponseDTO updateScoreAndNoteForSubmission(UpdateScoreRequest updateScoreDTO) {
        // Fetch JudgeSubmission by ID
        JudgeSubmission judgeSubmission = judgeSubmissionRepository
                .findById(updateScoreDTO.getJudgeSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("JudgeSubmission not found"));

        // Update the note
        judgeSubmission.setNote(updateScoreDTO.getNote());

        // Recalculate the score based on JudgeSubmissionDetails
        recalculateTotalScore(judgeSubmission);

        // Save updated JudgeSubmission
        judgeSubmission = judgeSubmissionRepository.save(judgeSubmission);

        // Return response DTO
        return new JudgeSubmissionResponseDTO(judgeSubmission);
    }

    private void recalculateTotalScore(JudgeSubmission judgeSubmission) {
        Set<JudgeSubmissionDetail> judgeSubmissionDetails = judgeSubmission.getJudgeSubmissionDetails();

        if (judgeSubmissionDetails != null && !judgeSubmissionDetails.isEmpty()) {
            int totalScore = judgeSubmissionDetails.stream()
                    .mapToInt(JudgeSubmissionDetail::getScore)
                    .sum();

            judgeSubmission.setScore(totalScore);
        } else {
            judgeSubmission.setScore(0);
        }
    }

    @Override
    public JudgeSubmissionResponseDTO getJudgeSubmissionBySubmissionId(Long submissionId) {
        List<JudgeSubmission> judgeSubmissions = judgeSubmissionRepository.findBySubmissionId(submissionId);

        if (judgeSubmissions.isEmpty()) {
            throw new IllegalArgumentException("Judge submission not found for submissionId: " + submissionId);
        }

        JudgeSubmission judgeSubmission = judgeSubmissions.get(0);

        return new JudgeSubmissionResponseDTO(judgeSubmission);
    }

    @Override
    public List<JudgeSubmissionResponseDTO> getAllJudgeSubmissions() {
        List<JudgeSubmission> judgeSubmissions = judgeSubmissionRepository.findAll();
        return judgeSubmissions.stream().map(JudgeSubmissionResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public boolean deleteJudgeSubmission(Long judgeSubmissionId) {
        JudgeSubmission judgeSubmission = judgeSubmissionRepository
                .findById(judgeSubmissionId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Judge submission not found for ID: " + judgeSubmissionId));

        judgeSubmissionRepository.delete(judgeSubmission);
        return true;
    }

    @Override
    public List<JudgeSubmissionResponseDTO> getSubmissionsByJudgeId(Long judgeId) {
        List<JudgeSubmission> judgeSubmissions = judgeSubmissionRepository.findByJudgeId(judgeId);
        if (judgeSubmissions.isEmpty()) {
            throw new IllegalArgumentException("No submissions found for judgeId: " + judgeId);
        }
        return judgeSubmissions.stream().map(JudgeSubmissionResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<JudgeSubmissionResponseDTO> getSubmissionsByRoundId(Long roundId) {
        List<JudgeSubmission> judgeSubmissions = judgeSubmissionRepository.findByRoundId(roundId);

        if (judgeSubmissions.isEmpty()) {
            throw new IllegalArgumentException("No submissions found for roundId: " + roundId);
        }
        return judgeSubmissions.stream().map(JudgeSubmissionResponseDTO::new).collect(Collectors.toList());
    }

    //    @Override
    //    public JudgeSubmissionResponseDTO getSubmissionScore(Long submissionId) {
    //        JudgeSubmission judgeSubmission = judgeSubmissionRepository
    //                .findBySubmissionId(submissionId)
    //                .orElseThrow(() -> new IllegalArgumentException("Judge submission not found"));
    //
    //        return new JudgeSubmissionResponseDTO(judgeSubmission);
    //    }
}
