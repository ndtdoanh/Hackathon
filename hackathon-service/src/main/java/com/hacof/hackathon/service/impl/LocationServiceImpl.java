package com.hacof.hackathon.service.impl;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.LocationMapper;
import com.hacof.hackathon.repository.LocationRepository;
import com.hacof.hackathon.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public List<LocationDTO> getAllLocations(Specification<Location> spec) {
        if(locationRepository.findAll(spec).isEmpty()){
            throw new ResourceNotFoundException("Location not found");
        }
        return locationRepository.findAll(spec).stream()
                .map(locationMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = locationMapper.convertToEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.convertToDTO(savedLocation);
    }

    @Override
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));
        existingLocation.setName(locationDTO.getName());
        existingLocation.setAddress(locationDTO.getAddress());
        existingLocation.setLatitude(locationDTO.getLatitude());
        existingLocation.setLongitude(locationDTO.getLongitude());
        Location updatedLocation = locationRepository.save(existingLocation);
        return locationMapper.convertToDTO(updatedLocation);
    }

    @Override
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location not found");
        }
        locationRepository.deleteById(id);
    }
}