package com.hacof.submission.services.impl;

import com.hacof.submission.dtos.request.EvaluationScoreRequestDTO;
import com.hacof.submission.dtos.response.EvaluationScoreResponseDTO;
import com.hacof.submission.entities.EvaluationScores;
import com.hacof.submission.mapper.EvaluationScoreMapper;
import com.hacof.submission.repositories.EvaluationScoreRepository;
import com.hacof.submission.repositories.EvaluationCriteriaRepository;
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
        return repository.findById(id).map(existingScore -> {
            existingScore.setScore(scoreDetails.getScore());
            existingScore.setComment(scoreDetails.getComment());
            String currentUser = SecurityUtil.getCurrentUserLogin().orElse("system");
            Instant now = Instant.now();

            existingScore.setLastUpdatedBy(currentUser);
            existingScore.setLastUpdatedAt(now);

            repository.save(existingScore);
            return mapper.toResponseDTO(existingScore);
        }).orElseThrow(() -> new RuntimeException("EvaluationScore not found with id: " + id));
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
