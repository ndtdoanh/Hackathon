package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.RoundStatus;
import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.RoundMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.service.RoundService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoundServiceImpl implements RoundService {
    private final RoundRepository roundRepository;
    private final HackathonRepository hackathonRepository;
    private final RoundMapper roundMapper;

    @Override
    public RoundDTO create(RoundDTO roundDTO) {
        //        Hackathon hackathon = hackathonRepository
        //                .findById(Long.parseLong(roundDTO.getHackathon().getId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
        Round round = roundMapper.toEntity(roundDTO);
        // round.setHackathon(hackathon);
        Round savedRound = roundRepository.save(round);
        return roundMapper.toDto(savedRound);
    }

    @Override
    public RoundDTO update(String id, RoundDTO roundDTO) {
        Round existingRound = roundRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        //        existingRound.setHackathon(hackathonRepository
        //                .findById(Long.parseLong(roundDTO.getHackathonId())
        //                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found")));
        existingRound.setStartTime(roundDTO.getStartTime());
        existingRound.setEndTime(roundDTO.getEndTime());
        existingRound.setRoundNumber(roundDTO.getRoundNumber());
        existingRound.setRoundTitle(roundDTO.getRoundTitle());
        existingRound.setStatus(RoundStatus.valueOf(roundDTO.getStatus().name()));
        // existingRound.setLastModifiedDate(roundDTO.getLastModifiedDate());

        Round updatedRound = roundRepository.save(existingRound);
        return roundMapper.toDto(updatedRound);
    }

    @Override
    public void delete(Long id) {
        if (!roundRepository.existsById(id)) {
            throw new ResourceNotFoundException("Round not found");
        }
        roundRepository.deleteById(id);
    }

    @Override
    public List<RoundDTO> getAll() {
        if (roundRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No rounds found");
        }
        return roundRepository.findAll().stream().map(roundMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RoundDTO getById(Long id) {
        if (!roundRepository.existsById(id)) {
            throw new ResourceNotFoundException("Round not found");
        }
        Round round = roundRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Round not found"));
        return roundMapper.toDto(round);
    }
}
