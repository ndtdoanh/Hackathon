package com.hacof.hackathon.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.LocationMapperManual;
import com.hacof.hackathon.repository.LocationRepository;
import com.hacof.hackathon.repository.RoundLocationRepository;
import com.hacof.hackathon.service.LocationService;
import com.hacof.hackathon.util.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true)
public class LocationServiceImpl implements LocationService {
    LocationRepository locationRepository;
    RoundLocationRepository roundLocationRepository;
    SecurityUtil securityUtil;

    @Override
    public LocationDTO create(LocationDTO locationDTO) {
        // validate Name unique
        if (locationRepository.existsByName(locationDTO.getName())) {
            throw new ResourceNotFoundException("Location name already exists");
        }

        Location location = LocationMapperManual.toEntity(locationDTO);
        return LocationMapperManual.toDto(locationRepository.save(location));
    }

    @Override
    public LocationDTO update(Long id, LocationDTO locationDTO) {
        Location existingLocation = locationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));

        // validate Name unique
        if (locationRepository.existsByName(locationDTO.getName())) {
            throw new ResourceNotFoundException("Location name already exists");
        }

        User currentUser = securityUtil.getAuthenticatedUser();

        existingLocation.setName(locationDTO.getName());
        existingLocation.setAddress(locationDTO.getAddress());
        existingLocation.setLongitude(locationDTO.getLongitude());
        existingLocation.setLatitude(locationDTO.getLatitude());

        User createdBy = existingLocation.getCreatedBy();
        existingLocation.setLastModifiedDate(locationDTO.getUpdatedAt());
        existingLocation.setLastModifiedBy(currentUser);
        existingLocation.setCreatedBy(createdBy);
        return LocationMapperManual.toDto(locationRepository.save(existingLocation));
    }

    @Override
    public void delete(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location not found");
        }
        roundLocationRepository.deleteById(id);
        locationRepository.deleteById(id);
    }

    @Override
    public List<LocationDTO> getLocations(Specification<Location> spec) {
        return locationRepository.findAll(spec).stream()
                .map(LocationMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Location getLocationEntityById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location not found"));
    }
}
