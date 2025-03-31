package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.RoundLocationDTO;

public interface RoundLocationService {
    RoundLocationDTO create(RoundLocationDTO roundLocationDTO);

    RoundLocationDTO update(Long id, RoundLocationDTO roundLocationDTO);

    void delete(Long id);

    List<RoundLocationDTO> getAll();

    RoundLocationDTO getById(Long id);
}
