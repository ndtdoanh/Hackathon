package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.LocationDTO;
import com.hacof.hackathon.entity.Location;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface LocationService {
    LocationDTO create(LocationDTO locationDTO);

    LocationDTO update(Long id, LocationDTO locationDTO);

    void delete(Long id);

    List<LocationDTO> getLocations(Specification<Location> spec);

    Location getLocationEntityById(Long id);
}
