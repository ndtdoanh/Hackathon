package com.hacof.submission.service.impl;

import com.hacof.submission.dto.request.JudgeSubmissionDetailRequestDTO;
import com.hacof.submission.dto.request.JudgeSubmissionRequestDTO;
import com.hacof.submission.dto.response.JudgeSubmissionResponseDTO;
import com.hacof.submission.entity.JudgeSubmission;
import com.hacof.submission.entity.JudgeSubmissionDetail;
import com.hacof.submission.entity.RoundMarkCriterion;
import com.hacof.submission.entity.Submission;
import com.hacof.submission.entity.User;
import com.hacof.submission.mapper.JudgeSubmissionMapper;
import com.hacof.submission.repository.JudgeSubmissionRepository;
import com.hacof.submission.repository.RoundMarkCriterionRepository;
import com.hacof.submission.repository.SubmissionRepository;
import com.hacof.submission.repository.TeamRoundJudgeRepository;
import com.hacof.submission.repository.UserRepository;
import com.hacof.submission.service.JudgeSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JudgeSubmissionServiceImpl implements JudgeSubmissionService {

    @Autowired
    private JudgeSubmissionRepository judgeSubmissionRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundMarkCriterionRepository roundMarkCriterionRepository;

    @Autowired
    private TeamRoundJudgeRepository teamRoundJudgeRepository;

    @Autowired
    private JudgeSubmissionMapper judgeSubmissionMapper;

    @Transactional
    @Override
    public JudgeSubmissionResponseDTO createJudgeSubmission(JudgeSubmissionRequestDTO requestDTO) {
        boolean alreadySubmitted = judgeSubmissionRepository.existsByJudgeIdAndSubmissionId(
                requestDTO.getJudgeId(), requestDTO.getSubmissionId());

        if (alreadySubmitted) {
            throw new IllegalArgumentException("The judge has already evaluated this submission!");
        }

        User judge = userRepository
                .findById(requestDTO.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found with ID: " + requestDTO.getJudgeId()));

        Submission submission = submissionRepository
                .findById(requestDTO.getSubmissionId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Submission not found with ID: " + requestDTO.getSubmissionId()));

        boolean isAssigned = teamRoundJudgeRepository.existsByJudgeAndRoundAndTeam(
                judge.getId(),
                submission.getRound().getId(),
                submission.getTeam().getId());

        if (!isAssigned) {
            throw new IllegalArgumentException("The judge has not been assigned to this round and cannot evaluate.");
        }

        // Lấy danh sách tiêu chí của round
        List<Long> requiredCriteria = roundMarkCriterionRepository.findCriterionIdsByRound(
                submission.getRound().getId());
        Set<Long> judgedCriteria = new HashSet<>();
        int totalScore = 0;

        Set<JudgeSubmissionDetail> details = new HashSet<>();

        JudgeSubmission judgeSubmission = new JudgeSubmission();
        judgeSubmission.setJudge(judge);
        judgeSubmission.setSubmission(submission);
        judgeSubmission.setScore(0);
        judgeSubmission.setNote(requestDTO.getNote());

        JudgeSubmission savedJudgeSubmission = judgeSubmissionRepository.save(judgeSubmission);

        for (JudgeSubmissionDetailRequestDTO detailRequest : requestDTO.getJudgeSubmissionDetails()) {
            if (judgedCriteria.contains(detailRequest.getRoundMarkCriterionId())) {
                throw new IllegalArgumentException("Each criterion can only be evaluated once.");
            }

            RoundMarkCriterion criterion = roundMarkCriterionRepository
                    .findById(detailRequest.getRoundMarkCriterionId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Criterion not found with ID: " + detailRequest.getRoundMarkCriterionId()));

            if (detailRequest.getScore() > criterion.getMaxScore()) {
                throw new IllegalArgumentException("Score exceeds max for criterion ID " + criterion.getId());
            }

            JudgeSubmissionDetail detail = new JudgeSubmissionDetail();
            detail.setJudgeSubmission(savedJudgeSubmission);
            detail.setRoundMarkCriterion(criterion);
            detail.setScore(detailRequest.getScore());
            detail.setNote(detailRequest.getNote());
            details.add(detail);

            totalScore += detailRequest.getScore();
            judgedCriteria.add(detailRequest.getRoundMarkCriterionId());
        }

        savedJudgeSubmission.setJudgeSubmissionDetails(details);

        // Chỉ cập nhật điểm nếu đã chấm hết tiêu chí
        if (judgedCriteria.containsAll(requiredCriteria)) {
            savedJudgeSubmission.setScore(totalScore);
        } else {
            savedJudgeSubmission.setScore(0);
        }

        savedJudgeSubmission = judgeSubmissionRepository.save(savedJudgeSubmission);

        updateFinalScoreIfAllJudgesSubmitted(submission);

        return judgeSubmissionMapper.toResponseDTO(savedJudgeSubmission);
    }

    @Transactional
    public void updateFinalScoreIfAllJudgesSubmitted(Submission submission) {
        // Lấy danh sách các giám khảo được giao chấm bài trong TeamRoundJudge
        List<User> assignedJudges = teamRoundJudgeRepository.findJudgesByRoundAndTeam(
                submission.getRound().getId(), submission.getTeam().getId());

        // Lấy danh sách giám khảo đã chấm bài xong
        List<User> judgesWhoCompleted = judgeSubmissionRepository.findJudgesWhoCompletedSubmission(submission.getId());

        // Nếu chưa đủ giám khảo chấm xong, giữ finalScore = null
        if (!new HashSet<>(judgesWhoCompleted).containsAll(assignedJudges)) {
            submission.setFinalScore(null);
            submissionRepository.save(submission);
            return;
        }

        // Tính tổng điểm của tất cả giám khảo đã chấm xong
        int totalScore = judgeSubmissionRepository.getTotalScoreBySubmission(submission.getId());
        int numberOfJudges = judgesWhoCompleted.size();
        int finalScore = numberOfJudges > 0 ? totalScore / numberOfJudges : 0;

        // Cập nhật finalScore vào Submission
        submission.setFinalScore(finalScore);
        submissionRepository.save(submission);
    }

    @Override
    public JudgeSubmissionResponseDTO getJudgeSubmissionById(Long id) {
        JudgeSubmission judgeSubmission = judgeSubmissionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JudgeSubmission not found with ID: " + id));
        return judgeSubmissionMapper.toResponseDTO(judgeSubmission);
    }

    @Override
    public List<JudgeSubmissionResponseDTO> getAllJudgeSubmissions() {
        return judgeSubmissionRepository.findAll().stream()
                .map(judgeSubmissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public JudgeSubmissionResponseDTO updateJudgeSubmission(Long id, JudgeSubmissionRequestDTO requestDTO) {
        JudgeSubmission judgeSubmission = judgeSubmissionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JudgeSubmission not found with ID: " + id));

        Submission submission = judgeSubmission.getSubmission();
        List<Long> requiredCriteria = roundMarkCriterionRepository.findCriterionIdsByRound(
                submission.getRound().getId());

        judgeSubmission.getJudgeSubmissionDetails().clear();

        Set<Long> judgedCriteria = new HashSet<>();
        int totalScore = 0;

        for (JudgeSubmissionDetailRequestDTO detailRequest : requestDTO.getJudgeSubmissionDetails()) {
            if (judgedCriteria.contains(detailRequest.getRoundMarkCriterionId())) {
                throw new IllegalArgumentException("Each criterion can only be evaluated once.");
            }

            RoundMarkCriterion criterion = roundMarkCriterionRepository
                    .findById(detailRequest.getRoundMarkCriterionId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Criterion not found with ID: " + detailRequest.getRoundMarkCriterionId()));

            if (detailRequest.getScore() > criterion.getMaxScore()) {
                throw new IllegalArgumentException("Score exceeds max for criterion ID " + criterion.getId());
            }

            JudgeSubmissionDetail detail = new JudgeSubmissionDetail();
            detail.setRoundMarkCriterion(criterion);
            detail.setScore(detailRequest.getScore());
            detail.setNote(detailRequest.getNote());
            detail.setJudgeSubmission(judgeSubmission);
            judgeSubmission.getJudgeSubmissionDetails().add(detail);

            totalScore += detailRequest.getScore();
            judgedCriteria.add(detailRequest.getRoundMarkCriterionId());
        }

        // Chỉ cập nhật điểm nếu đã chấm hết tiêu chí
        if (judgedCriteria.containsAll(requiredCriteria)) {
            judgeSubmission.setScore(totalScore);
        } else {
            judgeSubmission.setScore(0);
        }

        JudgeSubmission updatedJudgeSubmission = judgeSubmissionRepository.save(judgeSubmission);

        // Nếu tất cả giám khảo đã chấm xong, cập nhật `finalScore`
        updateFinalScoreIfAllJudgesSubmitted(submission);

        return judgeSubmissionMapper.toResponseDTO(updatedJudgeSubmission);
    }

    @Override
    public void deleteJudgeSubmission(Long id) {
        JudgeSubmission judgeSubmission = judgeSubmissionRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JudgeSubmission not found with ID: " + id));
        judgeSubmissionRepository.delete(judgeSubmission);
    }

    @Override
    public List<JudgeSubmissionResponseDTO> getSubmissionsByJudgeId(Long judgeId) {
        List<JudgeSubmission> judgeSubmissions = judgeSubmissionRepository.findByJudgeId(judgeId);
        if (judgeSubmissions.isEmpty()) {
            throw new IllegalArgumentException("No submissions found for judge ID: " + judgeId);
        }
        return judgeSubmissions.stream()
                .map(judgeSubmissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JudgeSubmissionResponseDTO> getSubmissionsByRoundId(Long roundId) {
        List<JudgeSubmission> judgeSubmissions = judgeSubmissionRepository.findByRoundId(roundId);
        if (judgeSubmissions.isEmpty()) {
            throw new IllegalArgumentException("No submissions found for round ID: " + roundId);
        }
        return judgeSubmissions.stream()
                .map(judgeSubmissionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
