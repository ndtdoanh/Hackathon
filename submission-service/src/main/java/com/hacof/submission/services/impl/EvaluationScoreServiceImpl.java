package com.hacof.submission.services.impl;

import com.hacof.submission.dtos.request.EvaluationScoreRequestDTO;
import com.hacof.submission.dtos.response.EvaluationScoreResponseDTO;
import com.hacof.submission.entities.EvaluationCriteria;
import com.hacof.submission.entities.EvaluationScores;
import com.hacof.submission.entities.Submission;
import com.hacof.submission.entities.User;
import com.hacof.submission.mapper.EvaluationScoreMapper;
import com.hacof.submission.repositories.*;
import com.hacof.submission.services.EvaluationScoreService;
import com.hacof.submission.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationScoreServiceImpl implements EvaluationScoreService {

    @Autowired
    private EvaluationScoreRepository repository;

    @Autowired
    private EvaluationCriteriaRepository criteriaRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationScoreMapper mapper;

    @Override
    public List<EvaluationScoreResponseDTO> getAllScores() {
        List<EvaluationScores> scores = repository.findAll();
        return scores.stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public EvaluationScoreResponseDTO getScoreById(Integer id) {
        Optional<EvaluationScores> score = repository.findById(id);
        return score.map(mapper::toResponseDTO).orElse(null);
    }

    @Override
    public EvaluationScoreResponseDTO createScore(EvaluationScoreRequestDTO scoreDTO) {
        Submission submission = submissionRepository.findById(scoreDTO.getSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + scoreDTO.getSubmissionId()));
        User judge = userRepository.findById(scoreDTO.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found with id: " + scoreDTO.getJudgeId()));
        EvaluationCriteria criteria = criteriaRepository.findById(scoreDTO.getEvaluationCriteriaId())
                .orElseThrow(() -> new IllegalArgumentException("Evaluation Criteria not found!"));

        int scoreValue = scoreDTO.getScore();
        int maxAllowed = criteria.getMaxScore();
        if (scoreValue > maxAllowed) {
            throw new RuntimeException("Score " + scoreValue + " exceeds the maximum allowed score of " + maxAllowed);
        }

        EvaluationScores score = mapper.toEntity(scoreDTO);
        String currentUser = SecurityUtil.getCurrentUserLogin().orElse("system");
        Instant now = Instant.now();

        score.setCreatedAt(now);
        score.setCreatedBy(currentUser);
        score.setLastUpdatedAt(now);
        score.setLastUpdatedBy(currentUser);

        EvaluationScores savedScore = repository.save(score);
        return mapper.toResponseDTO(savedScore);
    }


    @Override
    public EvaluationScoreResponseDTO updateScore(Integer id, EvaluationScoreRequestDTO scoreDetails) {
        EvaluationScores existingScore = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EvaluationScore not found with id: " + id));
        Submission submission = submissionRepository.findById(scoreDetails.getSubmissionId())
                .orElseThrow(() -> new IllegalArgumentException("Submission not found with id: " + scoreDetails.getSubmissionId()));
        User judge = userRepository.findById(scoreDetails.getJudgeId())
                .orElseThrow(() -> new IllegalArgumentException("Judge not found with id: " + scoreDetails.getJudgeId()));
        EvaluationCriteria criteria = criteriaRepository.findById(scoreDetails.getEvaluationCriteriaId())
                .orElseThrow(() -> new IllegalArgumentException("Evaluation Criteria not found!"));

        int scoreValue = scoreDetails.getScore();
        int maxAllowed = criteria.getMaxScore();
        if (scoreValue > maxAllowed) {
            throw new RuntimeException("Score " + scoreValue + " exceeds the maximum allowed score of " + maxAllowed);
        }

        existingScore.setScore(scoreDetails.getScore());
        existingScore.setComment(scoreDetails.getComment());
        existingScore.setSubmission(submission);
        existingScore.setJudge(judge);

        String currentUser = SecurityUtil.getCurrentUserLogin().orElse("system");
        Instant now = Instant.now();
        existingScore.setLastUpdatedBy(currentUser);
        existingScore.setLastUpdatedAt(now);

        EvaluationScores updatedScore = repository.save(existingScore);
        return mapper.toResponseDTO(updatedScore);
    }


    @Override
    public boolean deleteScore(Integer id) {
        return repository.findById(id).map(existingScore -> {
            Instant now = Instant.now();
            existingScore.setDeletedAt(now);
            repository.save(existingScore);
            return true;
        }).orElse(false);
    }
}
