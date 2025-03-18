package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.RoundLocationType;
import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.dto.RoundLocationResponseDTO;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.RoundLocationMapper;
import com.hacof.hackathon.repository.LocationRepository;
import com.hacof.hackathon.repository.RoundLocationRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.service.RoundLocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoundLocationServiceImpl implements RoundLocationService {
    private final RoundLocationRepository roundLocationRepository;
    private final RoundLocationMapper roundLocationMapper;
    private final RoundRepository roundRepository;
    private final LocationRepository locationRepository;

    @Override
    public List<RoundLocationResponseDTO> getAllRoundLocations(Specification<RoundLocation> spec) {
        return roundLocationRepository.findAll(spec).stream()
                .map(roundLocationMapper::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoundLocationResponseDTO createRoundLocation(RoundLocationDTO roundLocationDTO) {
        RoundLocation roundLocation = roundLocationMapper.convertToEntity(roundLocationDTO);
        RoundLocation savedRoundLocation = roundLocationRepository.save(roundLocation);
        return roundLocationMapper.convertToResponseDTO(savedRoundLocation);
    }

    @Override
    public RoundLocationResponseDTO updateRoundLocation(Long id, RoundLocationDTO roundLocationDTO) {
        RoundLocation existingRoundLocation = roundLocationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Round location not found"));
        existingRoundLocation.setRound(roundRepository
                .findById(roundLocationDTO.getRoundId())
                .orElseThrow(() -> new ResourceNotFoundException("Round not found")));
        existingRoundLocation.setLocation(locationRepository
                .findById(roundLocationDTO.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found")));
        existingRoundLocation.setType(RoundLocationType.valueOf(roundLocationDTO.getType()));
        RoundLocation updatedRoundLocation = roundLocationRepository.save(existingRoundLocation);
        return roundLocationMapper.convertToResponseDTO(updatedRoundLocation);
    }

    @Override
    public void deleteRoundLocation(Long id) {
        if (!roundLocationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Round location not found");
        }
        roundLocationRepository.deleteById(id);
    }
}
