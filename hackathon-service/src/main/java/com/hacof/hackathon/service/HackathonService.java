package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.HackathonDTO;
import com.hacof.hackathon.entity.Hackathon;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface HackathonService {
    HackathonDTO create(HackathonDTO hackathonDTO);

    HackathonDTO update(String id, HackathonDTO hackathonDTO);

    void deleteHackathon(Long id);

    List<HackathonDTO> getHackathons(Specification<Hackathon> spec);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);
}
