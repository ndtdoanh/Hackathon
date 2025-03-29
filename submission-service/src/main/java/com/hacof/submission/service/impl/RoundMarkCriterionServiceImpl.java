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
                .map(mapper::toRoundMarkCriterionResponseDTO) // Ensure correct mapping method
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoundMarkCriterionResponseDTO> getById(Long id) {
        Optional<RoundMarkCriterion> criterion = repository.findById(id);
        return criterion.map(mapper::toRoundMarkCriterionResponseDTO);
    }

    @Override
    public RoundMarkCriterionResponseDTO create(RoundMarkCriterionRequestDTO criterionDTO) {
        Optional<Round> roundOpt = roundRepository.findById(criterionDTO.getRoundId());
        if (!roundOpt.isPresent()) {
            throw new IllegalArgumentException("Round not found with id " + criterionDTO.getRoundId());
        }

        RoundMarkCriterion criterion = mapper.toEntity(criterionDTO);
        criterion.setRound(roundOpt.get());

        /// Lấy thông tin người dùng hiện tại từ SecurityUtil
        String currentUser = SecurityUtil.getCurrentUserLogin().orElse("anonymousUser");
        if ("anonymousUser".equals(currentUser)) {
            currentUser = "admin";
        }

        Optional<User> userOpt = userRepository.findByUsername(currentUser);
        User user = userOpt.orElseThrow(() ->
                new IllegalArgumentException("User not found with username "));
        criterion.setCreatedBy(user);

        RoundMarkCriterion savedCriterion = repository.save(criterion);
        return mapper.toRoundMarkCriterionResponseDTO(savedCriterion); // Fix method call
    }


    @Override
    public RoundMarkCriterionResponseDTO update(Long id, RoundMarkCriterionRequestDTO updatedCriterionDTO) {
        RoundMarkCriterion updated = repository
                .findById(id)
                .map(criterion -> {
                    Optional<Round> roundOpt = roundRepository.findById(updatedCriterionDTO.getRoundId());
                    if (!roundOpt.isPresent()) {
                        throw new IllegalArgumentException(
                                "Round not found with id " + updatedCriterionDTO.getRoundId());
                    }

                    criterion.setName(updatedCriterionDTO.getName());
                    criterion.setNote(updatedCriterionDTO.getNote());
                    criterion.setMaxScore(updatedCriterionDTO.getMaxScore());
                    criterion.setRound(roundOpt.get());
                    return repository.save(criterion);
                })
                .orElseThrow(() -> new IllegalArgumentException("RoundMarkCriterion not found with id " + id));

        return mapper.toRoundMarkCriterionResponseDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Optional<RoundMarkCriterion> criterionOptional = repository.findById(id);
        if (criterionOptional.isPresent()) {
            repository.delete(criterionOptional.get());
        } else {
            throw new IllegalArgumentException("Round mark criterion with id " + id + " not found");
        }
    }
}
