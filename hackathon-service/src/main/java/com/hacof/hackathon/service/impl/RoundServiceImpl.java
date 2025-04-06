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
import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.RoundLocationMapper;
import com.hacof.hackathon.mapper.RoundMapper;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.RoundService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(makeFinal = true)
public class RoundServiceImpl implements RoundService {
    RoundRepository roundRepository;
    HackathonRepository hackathonRepository;
    UserRepository userRepository;
    RoundMapper roundMapper;
    RoundLocationMapper roundLocationMapper;

    @Override
    public RoundDTO create(RoundDTO roundDTO) {
        log.debug("Creating round with data: {}", roundDTO);
        // Validate hackathon exists
        Hackathon hackathon = validateHackathon(roundDTO.getHackathonId());

        // Validate round number is unique for hackathon
        validateUniqueRoundNumber(null, roundDTO.getRoundNumber(), hackathon.getId());

        // Validate round dates within hackathon dates
        validateRoundDates(roundDTO.getStartTime(), roundDTO.getEndTime(), hackathon);

        Round round = roundMapper.toEntity(roundDTO);
        round.setHackathon(hackathon);

        List<RoundLocation> roundLocations = roundDTO.getRoundLocations().stream()
                .map(dto -> {
                    RoundLocation entity = mapToEntity(dto);
                    log.debug("RoundLocation entity: {}", entity);

                    if (dto.getLocationId() != null && !dto.getLocationId().isBlank()) {
                        entity.setLocation(new Location());
                        entity.getLocation().setId(Long.parseLong(dto.getLocationId()));
                        log.debug("Location ID set: {}", dto.getLocationId());
                    } else {
                        throw new InvalidInputException("Location ID must not be null or blank");
                    }

                    entity.setRound(round);
                    return entity;
                })
                .collect(Collectors.toList());

        round.setRoundLocations(roundLocations);
        Round savedRound = roundRepository.save(round);

        // fetch locationId
        RoundDTO savedRoundDTO = roundMapper.toDto(savedRound);
        savedRoundDTO.setRoundLocations(savedRound.getRoundLocations().stream()
                .map(roundLocationMapper::toDto)
                .collect(Collectors.toList()));

        return savedRoundDTO;
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

        if (roundDTO.getRoundNumber() != existingRound.getRoundNumber()) {
            validateUniqueRoundNumber(
                    existingRound.getId(),
                    roundDTO.getRoundNumber(),
                    existingRound.getHackathon().getId());
        }

        validateRoundDates(roundDTO.getStartTime(), roundDTO.getEndTime(), existingRound.getHackathon());

        Authentication authentication = getAuthenticatedUser();

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

        List<RoundLocation> roundLocations = roundDTO.getRoundLocations().stream()
                .map(dto -> {
                    RoundLocation entity = mapToEntity(dto);

                    if (dto.getLocationId() != null && !dto.getLocationId().isBlank()) {
                        entity.setLocation(new Location());
                        entity.getLocation().setId(Long.parseLong(dto.getLocationId()));
                    } else {
                        throw new InvalidInputException("Location ID must not be null or blank");
                    }

                    entity.setRound(existingRound);
                    return entity;
                })
                .collect(Collectors.toList());

        existingRound.setRoundLocations(roundLocations);
        Round savedRound = roundRepository.save(existingRound);

        // Fetch locationId
        RoundDTO savedRoundDTO = roundMapper.toDto(savedRound);
        savedRoundDTO.setRoundLocations(savedRound.getRoundLocations().stream()
                .map(roundLocationMapper::toDto)
                .collect(Collectors.toList()));

        return savedRoundDTO;
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

    @Override
    public List<RoundDTO> getAllByHackathonId(String hackathonId) {
        if (hackathonId == null || hackathonId.isEmpty()) {
            throw new InvalidInputException("Hackathon ID is required");
        }
        if (roundRepository.existsByHackathonId(Long.parseLong(hackathonId))) {
            throw new ResourceNotFoundException("Hackathon not found");
        }
        List<Round> rounds = roundRepository.findAllByHackathonId(Long.parseLong(hackathonId));
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

    private RoundLocation mapToEntity(RoundLocationDTO dto) {
        RoundLocation entity = new RoundLocation();
        if (dto.getId() != null) {
            entity.setId(Long.parseLong(dto.getId()));
        }
        if (dto.getLocationId() != null && !dto.getLocationId().isBlank()) {
            entity.setLocation(new Location());
            entity.getLocation().setId(Long.parseLong(dto.getLocationId()));
        } else {
            throw new InvalidInputException("Location ID must not be null or blank");
        }
        entity.setType(dto.getType());
        return entity;
    }

    private Authentication getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return authentication;
    }
}
