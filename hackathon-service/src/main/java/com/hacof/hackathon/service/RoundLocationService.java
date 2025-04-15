package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.RoundLocationDTO;

import java.util.List;

public interface RoundLocationService {
    RoundLocationDTO create(RoundLocationDTO roundLocationDTO);

    RoundLocationDTO update(Long id, RoundLocationDTO roundLocationDTO);

    void delete(Long id);

    List<RoundLocationDTO> getAll();

    RoundLocationDTO getById(Long id);

    void deleteByLocationId(Long locationId);

    void deleteByRoundId(Long roundId);
}
