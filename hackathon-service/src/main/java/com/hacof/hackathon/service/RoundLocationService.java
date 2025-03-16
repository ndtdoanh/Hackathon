package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.RoundLocationDTO;
import com.hacof.hackathon.dto.RoundLocationResponseDTO;
import com.hacof.hackathon.entity.RoundLocation;

public interface RoundLocationService {
    List<RoundLocationResponseDTO> getAllRoundLocations(Specification<RoundLocation> spec);

    RoundLocationResponseDTO createRoundLocation(RoundLocationDTO roundLocationDTO);

    RoundLocationResponseDTO updateRoundLocation(Long id, RoundLocationDTO roundLocationDTO);

    void deleteRoundLocation(Long id);
}
