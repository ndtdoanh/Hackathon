package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.RoundDTO;

public interface RoundService {
    RoundDTO create(RoundDTO roundDTO);

    RoundDTO update(String id, RoundDTO roundDTO);

    void delete(Long id);

    List<RoundDTO> getAll();

    RoundDTO getById(Long id);
}
