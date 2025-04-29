package com.hacof.submission.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.submission.dto.request.RoundMarkCriterionRequestDTO;
import com.hacof.submission.dto.response.RoundMarkCriterionResponseDTO;
import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.RoundMarkCriterion;
import com.hacof.submission.entity.User;
import com.hacof.submission.mapper.RoundMarkCriterionMapper;
import com.hacof.submission.repository.RoundMarkCriterionRepository;
import com.hacof.submission.repository.RoundRepository;
import com.hacof.submission.repository.UserRepository;
import com.hacof.submission.service.RoundMarkCriterionService;
import com.hacof.submission.util.SecurityUtil;

@Service
public class RoundMarkCriterionServiceImpl implements RoundMarkCriterionService {

    @Autowired
    private RoundMarkCriterionRepository repository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoundMarkCriterionMapper mapper;

    @Override
    public List<RoundMarkCriterionResponseDTO> getAll() {
        List<RoundMarkCriterion> criterionList = repository.findAll();
        return criterionList.stream()
                .map(mapper::toRoundMarkCriterionResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoundMarkCriterionResponseDTO> getById(Long id) {
        Optional<RoundMarkCriterion> criterion = repository.findById(id);
        return criterion.map(mapper::toRoundMarkCriterionResponseDTO);
    }

    @Override
    public RoundMarkCriterionResponseDTO create(RoundMarkCriterionRequestDTO criterionDTO) {
        Round round = roundRepository
                .findById(criterionDTO.getRoundId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Round not found with ID " + criterionDTO.getRoundId()));

        // Validate input
        if (criterionDTO.getName() == null || criterionDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Criterion name is required");
        }

        if (criterionDTO.getMaxScore() == null || criterionDTO.getMaxScore() <= 0) {
            throw new IllegalArgumentException("Maximum score must be greater than 0");
        }

        // Check duplicate name in the same round
        if (repository.existsByRoundAndName(round, criterionDTO.getName().trim())) {
            throw new IllegalArgumentException("Criterion name already exists in this round");
        }

        RoundMarkCriterion criterion = mapper.toEntity(criterionDTO, round);

        String currentUser = SecurityUtil.getCurrentUserLogin().orElse("admin");
        User user = userRepository
                .findByUsername(currentUser)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + currentUser));

        criterion.setCreatedBy(user);

        RoundMarkCriterion savedCriterion = repository.save(criterion);
        return mapper.toRoundMarkCriterionResponseDTO(savedCriterion);
    }

    @Override
    public RoundMarkCriterionResponseDTO update(Long id, RoundMarkCriterionRequestDTO updatedCriterionDTO) {
        RoundMarkCriterion criterion = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RoundMarkCriterion not found with id " + id));

        Round round = roundRepository
                .findById(updatedCriterionDTO.getRoundId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Round not found with ID " + updatedCriterionDTO.getRoundId()));

        if (updatedCriterionDTO.getName() == null
                || updatedCriterionDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Criterion name is required");
        }

        if (updatedCriterionDTO.getMaxScore() == null || updatedCriterionDTO.getMaxScore() <= 0) {
            throw new IllegalArgumentException("Maximum score must be greater than 0");
        }

        boolean isDuplicate = repository.existsByRoundAndName(
                        round, updatedCriterionDTO.getName().trim())
                && !criterion
                        .getName()
                        .equalsIgnoreCase(updatedCriterionDTO.getName().trim());

        if (isDuplicate) {
            throw new IllegalArgumentException("Criterion name already exists in this round");
        }

        criterion.setName(updatedCriterionDTO.getName().trim());
        criterion.setMaxScore(updatedCriterionDTO.getMaxScore());
        criterion.setNote(updatedCriterionDTO.getNote());
        criterion.setRound(round);

        RoundMarkCriterion updated = repository.save(criterion);
        return mapper.toRoundMarkCriterionResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        RoundMarkCriterion criterion = repository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Round mark criterion with id " + id + " not found"));

        repository.delete(criterion);
    }

    public List<RoundMarkCriterionResponseDTO> getByRoundId(Long roundId) {
        Round round = roundRepository
                .findById(roundId)
                .orElseThrow(() -> new IllegalArgumentException("Round not found with ID " + roundId));

        List<RoundMarkCriterion> criteriaList = repository.findByRound(round);
        return criteriaList.stream()
                .map(mapper::toRoundMarkCriterionResponseDTO)
                .collect(Collectors.toList());
    }
}
