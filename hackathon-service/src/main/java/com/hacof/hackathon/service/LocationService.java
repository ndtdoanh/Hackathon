package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;

public interface LocationService {
    LocationDTO createLocation(LocationDTO locationDTO);

    LocationDTO updateLocation(Long id, LocationDTO locationDTO);

    void deleteLocation(Long id);

    List<LocationDTO> getLocations(Specification<Location> spec);
}
