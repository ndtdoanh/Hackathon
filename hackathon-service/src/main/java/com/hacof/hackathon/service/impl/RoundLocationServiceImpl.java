package com.hacof.hackathon.service.impl;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.RoundLocationMapper;
import com.hacof.hackathon.repository.LocationRepository;
import com.hacof.hackathon.repository.RoundLocationRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.service.RoundLocationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(makeFinal = true)
public class RoundLocationServiceImpl implements RoundLocationService {
    RoundLocationRepository roundLocationRepository;
    RoundRepository roundRepository;
    LocationRepository locationRepository;
    RoundLocationMapper roundLocationMapper;

    @Override
    public RoundLocationDTO create(RoundLocationDTO roundLocationDTO) {
        Round round = roundRepository
                .findById(Long.parseLong(roundLocationDTO.getRoundId()))
                .orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        //        Location location = locationRepository
        //                .findById(Long.parseLong(roundLocationDTO.getLocationId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        //        //RoundLocation roundLocation = roundLocationMapper.toEntity(roundLocationDTO);
        ////        roundLocation.setRound(round);
        ////        // roundLocation.setLocation(location);
        ////
        ////        roundLocation = roundLocationRepository.save(roundLocation);
        ////        return roundLocationMapper.toDto(roundLocation);
        return null;
    }

    @Override
    public RoundLocationDTO update(Long id, RoundLocationDTO roundLocationDTO) {

        RoundLocation roundLocation = roundLocationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Round location not found"));

        Round round = roundRepository
                .findById(Long.parseLong(roundLocationDTO.getRoundId()))
                .orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        //        Location location = locationRepository
        //                .findById(Long.parseLong(roundLocationDTO.getLocationId()))
        //                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        // roundLocationMapper.updateEntityFromDto(roundLocationDTO, roundLocation);
        roundLocation.setRound(round);
        // roundLocation.setLocation(location);

        roundLocation = roundLocationRepository.save(roundLocation);
        return roundLocationMapper.toDto(roundLocation);
    }

    @Override
    public void delete(Long id) {
        if (!roundLocationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Round location not found");
        }
        roundLocationRepository.deleteById(id);
    }

    @Override
    public List<RoundLocationDTO> getAll() {
        if (roundLocationRepository.findAll().isEmpty()) {
            throw new ResourceNotFoundException("No round locations found");
        }

        return roundLocationRepository.findAll().stream()
                .map(roundLocationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoundLocationDTO getById(Long id) {
        RoundLocation roundLocation = roundLocationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Round location not found"));
        return roundLocationMapper.toDto(roundLocation);
    }

    @Override
    public void deleteByLocationId(Long locationId) {
        List<RoundLocation> roundLocations = roundLocationRepository.findByLocationId(locationId);
        if (roundLocations.isEmpty()) {
            throw new ResourceNotFoundException("Round locations not found for locationId: " + locationId);
        }
        roundLocationRepository.deleteAll(roundLocations);
    }

    @Override
    public void deleteByRoundId(Long roundId) {
        List<RoundLocation> roundLocations = roundLocationRepository.findByRoundId(roundId);
        if (roundLocations.isEmpty()) {
            throw new ResourceNotFoundException("Round locations not found for roundId: " + roundId);
        }
        roundLocationRepository.deleteAll(roundLocations);
    }
}
