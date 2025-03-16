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
    public List<RoundDTO> getRoundByAllCriteria(Specification<Round> spec) {
        if (roundRepository.findAll(spec).isEmpty()) {
            throw new ResourceNotFoundException("No rounds found");
        }
        return roundRepository.findAll(spec).stream()
                .map(roundMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoundDTO createRound(RoundDTO roundDTO) {
        Round round = roundMapper.convertToEntity(roundDTO);
        Round savedRound = roundRepository.save(round);
        return roundMapper.convertToDTO(savedRound);
    }

    @Override
    public RoundDTO updateRound(Long id, RoundDTO roundDTO) {
        Round existingRound =
                roundRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        existingRound.setRoundName(roundDTO.getRoundName());
        existingRound.setDescription(roundDTO.getDescription());
        existingRound.setStartTime(roundDTO.getStartDate());
        existingRound.setEndTime(roundDTO.getEndDate());
        existingRound.setMaxTeam(roundDTO.getMaxTeam()); //        existingRound.setVideoRound(roundDTO.getIsVideo());

        Round updatedRound = roundRepository.save(existingRound);
        return roundMapper.convertToDTO(updatedRound);
    }

    @Override
    public void deleteRound(Long id) {
        if (!roundRepository.existsById(id)) {
            throw new ResourceNotFoundException("Round not found");
        }
        roundRepository.deleteById(id);
    }
}
