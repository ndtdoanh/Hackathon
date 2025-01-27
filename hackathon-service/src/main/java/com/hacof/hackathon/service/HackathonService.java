package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.HackathonDTO;

public interface HackathonService {
    List<HackathonDTO> getAllHackathons();

    HackathonDTO getHackathonById(Long id);

    HackathonDTO createHackathon(HackathonDTO hackathonDTO);

    HackathonDTO updateHackathon(Long id, HackathonDTO hackathonDTO);

    void deleteHackathon(Long id);
}
