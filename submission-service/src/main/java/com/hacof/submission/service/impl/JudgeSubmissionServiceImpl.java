package com.hacof.submission.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.hacof.submission.constant.TeamRoundStatus;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.TeamRound;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.TeamRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TeamRoundRepository teamRoundRepository;

    @Autowired
    private RoundRepository roundRepository;

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
        List<User> assignedJudges = teamRoundJudgeRepository.findJudgesByRoundAndTeam(
                submission.getRound().getId(), submission.getTeam().getId());

        List<User> judgesWhoCompleted = judgeSubmissionRepository.findJudgesWhoCompletedSubmission(submission.getId());

        if (!new HashSet<>(judgesWhoCompleted).containsAll(assignedJudges)) {
            submission.setFinalScore(null);
            submissionRepository.save(submission);
            return;
        }

        int totalScore = judgeSubmissionRepository.getTotalScoreBySubmission(submission.getId());
        int numberOfJudges = judgesWhoCompleted.size();
        int finalScore = numberOfJudges > 0 ? totalScore / numberOfJudges : 0;

        submission.setFinalScore(finalScore);
        submissionRepository.save(submission);

        List<Submission> allSubmissions = submissionRepository.findByRoundId(submission.getRound().getId());
        boolean allScored = allSubmissions.stream().allMatch(s -> s.getFinalScore() != null);
        if (!allScored) return;

        List<Submission> sorted = allSubmissions.stream()
                .sorted((a, b) -> {
                    int scoreCompare = Integer.compare(b.getFinalScore(), a.getFinalScore());
                    if (scoreCompare != 0) return scoreCompare;
                    if (a.getSubmittedAt() == null && b.getSubmittedAt() == null) return 0;
                    if (a.getSubmittedAt() == null) return 1;
                    if (b.getSubmittedAt() == null) return -1;
                    return a.getSubmittedAt().compareTo(b.getSubmittedAt());
                })
                .collect(Collectors.toList());

        int totalTeam = submission.getRound().getTotalTeam();

        for (int i = 0; i < sorted.size(); i++) {
            Submission s = sorted.get(i);
            Long teamId = s.getTeam().getId();
            Long roundId = s.getRound().getId();

            TeamRound teamRound = teamRoundRepository.findByTeamIdAndRoundId(teamId, roundId)
                    .orElseThrow(() -> new IllegalArgumentException("TeamRound not found"));

            if (i < totalTeam) {
                teamRound.setStatus(TeamRoundStatus.PASSED);
                teamRoundRepository.save(teamRound);

                // 👉 Gộp luôn logic tạo next round ở đây
                Round currentRound = teamRound.getRound();
                int nextRoundNumber = currentRound.getRoundNumber() + 1;
                Long hackathonId = currentRound.getHackathon().getId();

                Optional<Round> nextRoundOpt = roundRepository.findByHackathonIdAndRoundNumber(hackathonId, nextRoundNumber);

                if (nextRoundOpt.isPresent()) {
                    Round nextRound = nextRoundOpt.get();

                    boolean exists = teamRoundRepository.existsByTeamIdAndRoundId(
                            teamRound.getTeam().getId(), nextRound.getId());

                    if (!exists) {
                        TeamRound nextTeamRound = new TeamRound();
                        nextTeamRound.setTeam(teamRound.getTeam());
                        nextTeamRound.setRound(nextRound);
                        nextTeamRound.setStatus(TeamRoundStatus.PENDING);
                        nextTeamRound.setDescription("Auto-generated for next round");

                        teamRoundRepository.save(nextTeamRound);
                    }
                }
            } else {
                teamRound.setStatus(TeamRoundStatus.FAILED);
                teamRoundRepository.save(teamRound);
            }
        }
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
