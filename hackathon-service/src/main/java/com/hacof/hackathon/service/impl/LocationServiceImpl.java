package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.LocationMapper;
import com.hacof.hackathon.repository.LocationRepository;
import com.hacof.hackathon.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public LocationDTO create(LocationDTO locationDTO) {
        Location location = locationMapper.toEntity(locationDTO);
        return locationMapper.toDto(locationRepository.save(location));
    }

    @Override
    public LocationDTO update(Long id, LocationDTO locationDTO) {
        Location existingLocation =
                locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location not found"));
        locationMapper.updateEntityFromDto(locationDTO, existingLocation);
        return locationMapper.toDto(locationRepository.save(existingLocation));
    }

    @Override
    public void delete(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location not found");
        }
        locationRepository.deleteById(id);
    }

    @Override
    public List<LocationDTO> getLocations(Specification<Location> spec) {
        return locationRepository.findAll(spec).stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }

    //    @Override
    //    public List<LocationDTO> getAll() {
    //        if (locationRepository.findAll().isEmpty()) {
    //            throw new ResourceNotFoundException("No locations found");
    //        }
    //        return locationRepository.findAll().stream().map(locationMapper::toDto).collect(Collectors.toList());
    //    }
    //
    //    @Override
    //    public LocationDTO getById(Long id) {
    //        Location location =
    //                locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location not
    // found"));
    //        return locationMapper.toDto(location);
    //    }
}
