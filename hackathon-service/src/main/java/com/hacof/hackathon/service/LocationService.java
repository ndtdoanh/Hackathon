package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;

public interface LocationService {
    LocationDTO create(LocationDTO locationDTO);

    LocationDTO update(Long id, LocationDTO locationDTO);

    void delete(Long id);

    List<LocationDTO> getLocations(Specification<Location> spec);

    Location getLocationEntityById(Long id);
}
