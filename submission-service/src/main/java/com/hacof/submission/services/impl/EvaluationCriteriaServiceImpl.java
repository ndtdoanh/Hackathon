package com.hacof.submission.services.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.submission.dtos.request.EvaluationCriteriaRequestDTO;
import com.hacof.submission.dtos.response.EvaluationCriteriaResponseDTO;
import com.hacof.submission.entities.EvaluationCriteria;
import com.hacof.submission.entities.Hackathon;
import com.hacof.submission.mapper.EvaluationCriteriaMapper;
import com.hacof.submission.repositories.EvaluationCriteriaRepository;
import com.hacof.submission.repositories.HackathonRepository;
import com.hacof.submission.services.EvaluationCriteriaService;
import com.hacof.submission.utils.SecurityUtil;

@Service
public class EvaluationCriteriaServiceImpl implements EvaluationCriteriaService {

    @Autowired
    private EvaluationCriteriaRepository repository;

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private EvaluationCriteriaMapper mapper;

    @Override
    public List<EvaluationCriteriaResponseDTO> getAll() {
        List<EvaluationCriteria> criteriaList = repository.findAll();
        return criteriaList.stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<EvaluationCriteriaResponseDTO> getById(Integer id) {
        Optional<EvaluationCriteria> criteria = repository.findById(id);
        return criteria.map(mapper::toResponseDTO);
    }

    @Override
    public EvaluationCriteriaResponseDTO create(EvaluationCriteriaRequestDTO criteriaDTO) {
        // Lấy Hackathon từ database bằng hackathonId
        Optional<Hackathon> hackathonOpt = hackathonRepository.findById(criteriaDTO.getHackathonId());
        if (!hackathonOpt.isPresent()) {
            throw new RuntimeException("Hackathon not found with id " + criteriaDTO.getHackathonId());
        }

        EvaluationCriteria criteria = mapper.toEntity(criteriaDTO);

        // Gán Hackathon vào EvaluationCriteria
        criteria.setHackathon(hackathonOpt.get());

        String currentUser = SecurityUtil.getCurrentUserLogin().orElse("system");
        Instant now = Instant.now();

        criteria.setCreatedAt(now);
        criteria.setCreatedBy(currentUser);
        criteria.setLastUpdatedAt(now);
        criteria.setLastUpdatedBy(currentUser);

        EvaluationCriteria savedCriteria = repository.save(criteria);
        return mapper.toResponseDTO(savedCriteria);
    }

    @Override
    public EvaluationCriteriaResponseDTO update(Integer id, EvaluationCriteriaRequestDTO updatedCriteriaDTO) {
        EvaluationCriteria updated = repository
                .findById(id)
                .map(criteria -> {
                    Optional<Hackathon> hackathonOpt =
                            hackathonRepository.findById(updatedCriteriaDTO.getHackathonId());
                    if (!hackathonOpt.isPresent()) {
                        throw new RuntimeException(
                                "Hackathon not found with id " + updatedCriteriaDTO.getHackathonId());
                    }

                    String currentUser = SecurityUtil.getCurrentUserLogin().orElse("system");
                    Instant now = Instant.now();

                    criteria.setName(updatedCriteriaDTO.getName());
                    criteria.setDescription(updatedCriteriaDTO.getDescription());
                    criteria.setMaxScore(updatedCriteriaDTO.getMaxScore());
                    criteria.setWeight(updatedCriteriaDTO.getWeight());
                    criteria.setHackathon(hackathonOpt.get());
                    criteria.setLastUpdatedBy(currentUser);
                    criteria.setLastUpdatedAt(now);

                    return repository.save(criteria);
                })
                .orElseThrow(() -> new RuntimeException("EvaluationCriteria not found with id " + id));

        return mapper.toResponseDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        Optional<EvaluationCriteria> criteriaOptional = repository.findById(id);
        if (criteriaOptional.isPresent()) {
            EvaluationCriteria criteria = criteriaOptional.get();
            Instant now = Instant.now();
            criteria.setDeletedAt(now);
            repository.save(criteria);
        } else {
            throw new RuntimeException("Evaluation criteria with id " + id + " not found");
        }
    }
}
