package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.RoundStatus;
import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.*;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.LocationMapper;
import com.hacof.hackathon.mapper.manual.RoundMapperManual;
import com.hacof.hackathon.repository.*;
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
    LocationRepository locationRepository;
    private RoundLocationRepository roundLocationRepository;

    LocationServiceImpl locationService;
    LocationMapper locationMapper;

    @Override
    public RoundDTO create(RoundDTO roundDTO) {
        validateEnums(roundDTO);
        Hackathon hackathon = validateHackathon(roundDTO.getHackathonId());
        validateUniqueRoundNumber(null, roundDTO.getRoundNumber(), hackathon.getId());
        validateRoundDates(roundDTO.getStartTime(), roundDTO.getEndTime(), hackathon);

        Round round = RoundMapperManual.toEntity(roundDTO);
        round.setHackathon(hackathon);

        // Set audit info
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        round.setCreatedBy(user);
        round.setLastModifiedBy(user);
        round.setCreatedDate(LocalDateTime.now());
        round.setLastModifiedDate(LocalDateTime.now());

        // Save round first to get its ID
        round = roundRepository.save(round);

        // Handle round locations
        if (roundDTO.getRoundLocations() != null) {
            for (RoundLocationDTO rlDTO : roundDTO.getRoundLocations()) {
                RoundLocation rl = new RoundLocation();
                rl.setRound(round);

                Long locationId = Long.parseLong(rlDTO.getLocationId());
                Location location = locationRepository
                        .findById(locationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Location not found: " + locationId));

                rl.setLocation(location);
                rl.setType(rlDTO.getType());
                rl.setCreatedBy(user);
                rl.setLastModifiedBy(user);
                rl.setCreatedDate(LocalDateTime.now());
                rl.setLastModifiedDate(LocalDateTime.now());

                roundLocationRepository.save(rl);
            }
        }

        round = roundRepository.findById(round.getId()).orElse(round);
        return RoundMapperManual.toDto(round);
    }

    @Override
    public RoundDTO update(String id, RoundDTO roundDTO) {
        validateEnums(roundDTO);
        Round existingRound = roundRepository
                .findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        Hackathon hackathon = existingRound.getHackathon();
        if (hackathon == null) {
            throw new ResourceNotFoundException("Hackathon not found for this round");
        }
        if (!Objects.equals(roundDTO.getRoundNumber(), existingRound.getRoundNumber())) {
            validateUniqueRoundNumber(existingRound.getId(), roundDTO.getRoundNumber(), hackathon.getId());
        }

        // Kiểm tra thời gian round nằm trong thời gian của hackathon
        validateRoundDates(roundDTO.getStartTime(), roundDTO.getEndTime(), hackathon);

        Authentication authentication = getAuthenticatedUser();
        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        User createdBy = existingRound.getCreatedBy();

        // Cập nhật thông tin round
        existingRound.setRoundTitle(roundDTO.getRoundTitle());
        existingRound.setRoundNumber(roundDTO.getRoundNumber());
        existingRound.setStartTime(roundDTO.getStartTime());
        existingRound.setEndTime(roundDTO.getEndTime());
        existingRound.setStatus(RoundStatus.valueOf(roundDTO.getStatus()));
        existingRound.setLastModifiedBy(currentUser);
        existingRound.setLastModifiedDate(LocalDateTime.now());
        existingRound.setCreatedBy(createdBy);
        existingRound.getRoundLocations().clear();

        for (RoundLocationDTO rlDTO : roundDTO.getRoundLocations()) {
            Location location = locationRepository
                    .findById(Long.parseLong(rlDTO.getLocationId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Location not found: " + rlDTO.getLocationId()));

            RoundLocation rl = new RoundLocation();
            rl.setRound(existingRound);
            rl.setLocation(location);
            rl.setType(rlDTO.getType());
            rl.setCreatedBy(currentUser);
            rl.setLastModifiedBy(currentUser);
            rl.setCreatedDate(LocalDateTime.now());
            rl.setLastModifiedDate(LocalDateTime.now());

            existingRound.getRoundLocations().add(rl);
        }

        Round updatedRound = roundRepository.save(existingRound);

        return RoundMapperManual.toDto(updatedRound);
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
        return rounds.stream().map(RoundMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public List<RoundDTO> getAllByHackathonId(String hackathonId) {
        if (hackathonId == null || hackathonId.isEmpty()) {
            throw new InvalidInputException("Hackathon ID is required");
        }

        List<Round> rounds = roundRepository.findAllByHackathonId(Long.parseLong(hackathonId));
        return rounds.stream().map(RoundMapperManual::toDto).collect(Collectors.toList());
    }

    @Override
    public RoundDTO getById(String id) {
        Optional<Round> roundOptional = roundRepository.findById(Long.parseLong(id));
        return roundOptional.map(RoundMapperManual::toDto).orElse(null);
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

    private Authentication getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return authentication;
    }

    private void validateEnums(RoundDTO roundDTO) {
        if (roundDTO.getStatus() != null) {
            try {
                RoundStatus.valueOf(roundDTO.getStatus());
            } catch (IllegalArgumentException e) {
                throw new InvalidInputException("Invalid round status: " + roundDTO.getStatus());
            }
        }
    }
}
