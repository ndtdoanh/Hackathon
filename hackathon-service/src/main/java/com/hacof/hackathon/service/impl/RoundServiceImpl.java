package com.hacof.hackathon.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.RoundStatus;
import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.InvalidInputException;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.RoundMapperManual;
import com.hacof.hackathon.repository.HackathonRepository;
import com.hacof.hackathon.repository.LocationRepository;
import com.hacof.hackathon.repository.RoundLocationRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.service.RoundService;
import com.hacof.hackathon.util.SecurityUtil;

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
    LocationRepository locationRepository;
    RoundLocationRepository roundLocationRepository;
    SecurityUtil securityUtil;

    @Override
    public RoundDTO create(RoundDTO roundDTO) {
        validateEnums(roundDTO);
        Hackathon hackathon = validateHackathon(roundDTO.getHackathonId());
        validateUniqueRoundNumber(null, roundDTO.getRoundNumber(), hackathon.getId());
        validateRoundDates(roundDTO.getStartTime(), roundDTO.getEndTime(), hackathon);

        // get the authenticated user
        User user = securityUtil.getAuthenticatedUser();
        LocalDateTime now = LocalDateTime.now();

        Round round = buildRound(roundDTO, hackathon, user, now);
        round = roundRepository.save(round);

        List<RoundLocation> savedRoundLocations = new ArrayList<>();

        if (roundDTO.getRoundLocations() != null) {
            for (RoundLocationDTO rlDTO : roundDTO.getRoundLocations()) {
                Long locationId = Long.parseLong(rlDTO.getLocationId());
                Location location = locationRepository
                        .findById(locationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Location not found: " + locationId));

                RoundLocation rl = buildRoundLocation(rlDTO, round, location, user, now);
                savedRoundLocations.add(roundLocationRepository.save(rl));
            }
        }

        round.setRoundLocations(savedRoundLocations);

        RoundDTO responseDTO = RoundMapperManual.toDto(round);
        responseDTO.setRoundLocations(savedRoundLocations.stream()
                .map(rl -> {
                    RoundLocationDTO dto = new RoundLocationDTO();
                    dto.setId(String.valueOf(rl.getId()));
                    dto.setLocationId(String.valueOf(rl.getLocation().getId()));
                    dto.setType(rl.getType());
                    dto.setCreatedByUserName(rl.getCreatedBy().getUsername());
                    dto.setLastModifiedByUserName(rl.getLastModifiedBy().getUsername());
                    dto.setCreatedAt(rl.getCreatedDate());
                    dto.setUpdatedAt(rl.getLastModifiedDate());

                    Location location = rl.getLocation();
                    if (location != null) {
                        dto.setLocation(mapToLocationDTO(location));
                    }

                    return dto;
                })
                .collect(Collectors.toList()));

        return responseDTO;
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

        validateRoundDates(roundDTO.getStartTime(), roundDTO.getEndTime(), hackathon);

        // get the authenticated user
        User currentUser = securityUtil.getAuthenticatedUser();
        User createdBy = existingRound.getCreatedBy();

        existingRound.setRoundTitle(roundDTO.getRoundTitle());
        existingRound.setTotalTeam(roundDTO.getTotalTeam());
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

            RoundLocation rl = buildRoundLocation(rlDTO, existingRound, location, currentUser, LocalDateTime.now());
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

    private void validateEnums(RoundDTO roundDTO) {
        if (roundDTO.getStatus() != null) {
            try {
                RoundStatus.valueOf(roundDTO.getStatus());
            } catch (IllegalArgumentException e) {
                throw new InvalidInputException("Invalid round status: " + roundDTO.getStatus());
            }
        }
    }

    private Round buildRound(RoundDTO roundDTO, Hackathon hackathon, User user, LocalDateTime now) {
        Round round = new Round();
        round.setHackathon(hackathon);
        round.setStartTime(roundDTO.getStartTime());
        round.setEndTime(roundDTO.getEndTime());
        round.setRoundNumber(roundDTO.getRoundNumber());
        round.setRoundTitle(roundDTO.getRoundTitle());
        round.setTotalTeam(roundDTO.getTotalTeam());
        round.setStatus(RoundStatus.valueOf(roundDTO.getStatus()));
        round.setCreatedBy(user);
        round.setLastModifiedBy(user);
        round.setCreatedDate(now);
        round.setLastModifiedDate(now);
        return round;
    }

    private LocationDTO mapToLocationDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(String.valueOf(location.getId()));
        locationDTO.setName(location.getName());
        locationDTO.setAddress(location.getAddress());
        locationDTO.setLatitude(location.getLatitude());
        locationDTO.setLongitude(location.getLongitude());

        if (location.getCreatedBy() != null) {
            locationDTO.setCreatedByUserName(location.getCreatedBy().getUsername());
        }

        if (location.getLastModifiedBy() != null) {
            locationDTO.setLastModifiedByUserName(location.getLastModifiedBy().getUsername());
        }

        locationDTO.setCreatedAt(location.getCreatedDate());
        locationDTO.setUpdatedAt(location.getLastModifiedDate());

        return locationDTO;
    }

    private RoundLocation buildRoundLocation(
            RoundLocationDTO rlDTO, Round round, Location location, User user, LocalDateTime now) {
        RoundLocation rl = new RoundLocation();
        rl.setRound(round);
        rl.setLocation(location);
        rl.setType(rlDTO.getType());
        rl.setCreatedBy(user);
        rl.setLastModifiedBy(user);
        rl.setCreatedDate(now);
        rl.setLastModifiedDate(now);
        return rl;
    }
}
