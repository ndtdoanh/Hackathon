package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.RoundDTO;
import com.hacof.hackathon.entity.Round;

public interface RoundService {
    List<RoundDTO> getRounds(Specification<Round> spec);

    RoundDTO createRound(RoundDTO roundDTO);

    RoundDTO updateRound(Long id, RoundDTO roundDTO);

    void deleteRound(Long id);
}
