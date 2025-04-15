package com.hacof.hackathon.service.impl;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.manual.LocationMapperManual;
import com.hacof.hackathon.repository.LocationRepository;
import com.hacof.hackathon.repository.RoundLocationRepository;
import com.hacof.hackathon.repository.UserRepository;
import com.hacof.hackathon.service.LocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true)
public class LocationServiceImpl implements LocationService {
    LocationRepository locationRepository;
    RoundLocationRepository roundLocationRepository;
    UserRepository userRepository;

    @Override
    public LocationDTO create(LocationDTO locationDTO) {
        // validate Name unique
        if (locationRepository.existsByName(locationDTO.getName())) {
            throw new ResourceNotFoundException("Location name already exists");
        }

        //        Location location = locationMapper.toEntity(locationDTO);
        //        return locationMapper.toDto(locationRepository.save(location));
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        String username = authentication.getName();
        User currentUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

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
        //        if (locationRepository.findAll(spec).isEmpty()) {
        //            throw new ResourceNotFoundException("Location not found");
        //        }

        return locationRepository.findAll(spec).stream()
                .map(LocationMapperManual::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Location getLocationEntityById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location not found"));
    }
}
