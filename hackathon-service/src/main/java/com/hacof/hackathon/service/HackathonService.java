package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;

public interface HackathonService {
    List<HackathonDTO> getByAllCriteria(Specification<Hackathon> spec);

    HackathonDTO createHackathon(HackathonDTO hackathonDTO);

    HackathonDTO updateHackathon(Long id, HackathonDTO hackathonDTO);

    void deleteHackathon(Long id);

    List<HackathonDTO> getAllHackathon(Specification<Hackathon> spec);
}
