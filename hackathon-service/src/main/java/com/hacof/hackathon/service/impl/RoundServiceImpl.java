package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.RoundMapper;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.service.RoundService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoundServiceImpl implements RoundService {
    final RoundRepository roundRepository;
    final RoundMapper roundMapper;

    @Override
    public List<RoundDTO> getRounds(Specification<Round> spec) {
        if (roundRepository.findAll(spec).isEmpty()) {
            throw new ResourceNotFoundException("No rounds found");
        }

        return roundRepository.findAll(spec).stream().map(roundMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RoundDTO createRound(RoundDTO roundDTO) {
        Round round = roundMapper.toEntity(roundDTO);
        Round savedRound = roundRepository.save(round);
        return roundMapper.toDTO(savedRound);
    }

    @Override
    public RoundDTO updateRound(Long id, RoundDTO roundDTO) {

        Round round = roundRepository.findById(id).orElseThrow(() -> new RuntimeException("Round not found"));
        roundMapper.updateEntityFromDTO(roundDTO, round);
        round = roundRepository.save(round);
        return roundMapper.toDTO(round);
    }

    @Override
    public void deleteRound(Long id) {
        if (!roundRepository.existsById(id)) {
            throw new ResourceNotFoundException("Round not found");
        }
        roundRepository.deleteById(id);
    }
}
