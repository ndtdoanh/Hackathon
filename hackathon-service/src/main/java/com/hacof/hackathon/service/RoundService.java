package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;

public interface RoundService {
    RoundDTO create(RoundDTO roundDTO);

    RoundDTO update(String id, RoundDTO roundDTO);

    void delete(Long id);

    List<RoundDTO> getRounds(Specification<Round> spec);

    List<RoundDTO> getAllByHackathonId(String hackathonId);

    RoundDTO getById(String id);
}
