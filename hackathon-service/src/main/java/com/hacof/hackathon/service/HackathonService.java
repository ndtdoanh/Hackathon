package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;

public interface HackathonService {
    HackathonDTO create(HackathonDTO hackathonDTO);

    HackathonDTO update(String id, HackathonDTO hackathonDTO);

    void deleteHackathon(Long id);

    List<HackathonDTO> getHackathons(Specification<Hackathon> spec);

    List<HackathonDTO> getHackathons();
}
