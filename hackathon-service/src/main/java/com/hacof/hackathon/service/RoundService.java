package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RoundService {
    RoundDTO create(RoundDTO roundDTO);

    RoundDTO update(String id, RoundDTO roundDTO);

    void delete(Long id);

    List<RoundDTO> getRounds(Specification<Round> spec);

    List<RoundDTO> getAllByHackathonId(String hackathonId);

    RoundDTO getById(String id);
}
