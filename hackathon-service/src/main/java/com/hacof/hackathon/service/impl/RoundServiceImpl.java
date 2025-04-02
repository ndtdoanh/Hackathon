package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.RoundMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final RoundMapper roundMapper;

    @Override
    public RoundDTO create(RoundDTO roundDTO) {

        // Validate hackathon exists
        Hackathon hackathon = validateHackathon(roundDTO.getHackathonId());

        // Validate round number is unique for hackathon
        validateUniqueRoundNumber(null, roundDTO.getRoundNumber(), hackathon.getId());

        // Validate round dates within hackathon dates
        validateRoundDates(roundDTO.getStartTime(), roundDTO.getEndTime(), hackathon);

        Round round = roundMapper.toEntity(roundDTO);
        round.setHackathon(hackathon);

        return roundMapper.toDto(roundRepository.save(round));
    }

    @Override
    public RoundDTO update(String id, RoundDTO roundDTO) {
        Round existingRound = roundRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        Hackathon hackathon = existingRound.getHackathon();
        if (hackathon == null) {
            throw new ResourceNotFoundException("Hackathon not found for this round");
        }

        // Validate round number uniqueness if changed
        if (roundDTO.getRoundNumber() != existingRound.getRoundNumber()) {
            validateUniqueRoundNumber(
                    existingRound.getId(),
                    roundDTO.getRoundNumber(),
                    existingRound.getHackathon().getId());
        }

        // Validate round dates
        validateRoundDates(roundDTO.getStartTime(), roundDTO.getEndTime(), existingRound.getHackathon());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        User createdBy = existingRound.getCreatedBy();
        existingRound.setRoundTitle(roundDTO.getRoundTitle());
        existingRound.setRoundNumber(roundDTO.getRoundNumber());
        existingRound.setStartTime(roundDTO.getStartTime());
        existingRound.setEndTime(roundDTO.getEndTime());
        existingRound.setStatus(roundDTO.getStatus());
        existingRound.setLastModifiedBy(currentUser);
        existingRound.setLastModifiedDate(roundDTO.getUpdatedAt());
        existingRound.setCreatedBy(createdBy);

        return roundMapper.toDto(roundRepository.save(existingRound));
    }

    @Override
    public void delete(Long id) {
        if (!roundRepository.existsById(id)) {
            throw new ResourceNotFoundException("Round not found");
        }
        roundRepository.deleteById(id);
    }

    @Override
    public List<RoundDTO> getRounds(Specification<Round> spec) {
        List<Round> rounds = roundRepository.findAll(spec);
        if (rounds.isEmpty()) {
            throw new ResourceNotFoundException("Round not found");
        }
        return rounds.stream().map(roundMapper::toDto).collect(Collectors.toList());
    }

    private Hackathon validateHackathon(String hackathonId) {
        return hackathonRepository
                .findById(Long.parseLong(hackathonId))
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon not found"));
    }

    private void validateUniqueRoundNumber(Long roundId, int roundNumber, Long hackathonId) {
        boolean exists = roundRepository.existsByRoundNumberAndHackathonIdAndIdNot(
                roundNumber, hackathonId, roundId != null ? roundId : 0L);
        if (exists) {
            throw new InvalidInputException("Round number " + roundNumber + " already exists in this hackathon");
        }
    }

    private void validateRoundDates(LocalDateTime startTime, LocalDateTime endTime, Hackathon hackathon) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidInputException("Start time must be before end time");
        }

        if (startTime.isBefore(hackathon.getStartDate()) || endTime.isAfter(hackathon.getEndDate())) {
            throw new InvalidInputException("Round dates must be within hackathon dates");
        }
    }
}
