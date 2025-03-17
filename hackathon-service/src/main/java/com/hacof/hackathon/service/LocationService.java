package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;

public interface LocationService {
    List<LocationDTO> getAllLocations(Specification<Location> spec);

    LocationDTO createLocation(LocationDTO locationDTO);

    LocationDTO updateLocation(Long id, LocationDTO locationDTO);

    void deleteLocation(Long id);
}
