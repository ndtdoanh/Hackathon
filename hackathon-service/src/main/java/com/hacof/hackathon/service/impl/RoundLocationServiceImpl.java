package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.Round;
import com.hacof.hackathon.entity.RoundLocation;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.RoundLocationMapper;
import com.hacof.hackathon.repository.LocationRepository;
import com.hacof.hackathon.repository.RoundLocationRepository;
import com.hacof.hackathon.repository.RoundRepository;
import com.hacof.hackathon.service.RoundLocationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoundLocationServiceImpl implements RoundLocationService {
    private final RoundLocationRepository roundLocationRepository;
    private final RoundLocationMapper roundLocationMapper;
    private final RoundRepository roundRepository;
    private final LocationRepository locationRepository;

    @Override
    public RoundLocationDTO create(RoundLocationDTO roundLocationDTO) {
        Round round = roundRepository
                .findById(Long.parseLong(roundLocationDTO.getRound().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Round not found"));

        Location location = locationRepository
                .findById(Long.parseLong(roundLocationDTO.getLocation().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
        RoundLocation roundLocation = roundLocationMapper.toEntity(roundLocationDTO);
        roundLocation.setRound(round);
        roundLocation.setLocation(location);
        return roundLocationMapper.toDto(roundLocationRepository.save(roundLocation));
    }

    @Override
    public RoundLocationDTO update(Long id, RoundLocationDTO roundLocationDTO) {
        RoundLocation existingRoundLocation = roundLocationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoundLocation not found"));
        roundLocationMapper.updateEntityFromDto(roundLocationDTO, existingRoundLocation);
        return roundLocationMapper.toDto(roundLocationRepository.save(existingRoundLocation));
    }

    @Override
    public void delete(Long id) {
        if (!roundLocationRepository.existsById(id)) {
            throw new ResourceNotFoundException("RoundLocation not found");
        }
        roundLocationRepository.deleteById(id);
    }

    @Override
    public List<RoundLocationDTO> getAll() {
        return roundLocationRepository.findAll().stream()
                .map(roundLocationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoundLocationDTO getById(Long id) {
        RoundLocation roundLocation = roundLocationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RoundLocation not found"));
        return roundLocationMapper.toDto(roundLocation);
    }
}
