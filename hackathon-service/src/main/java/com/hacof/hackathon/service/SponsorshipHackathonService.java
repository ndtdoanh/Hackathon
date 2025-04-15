package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.SponsorshipHackathonDTO;

import java.util.List;

public interface SponsorshipHackathonService {
    SponsorshipHackathonDTO create(SponsorshipHackathonDTO sponsorshipHackathonDTO);

    SponsorshipHackathonDTO update(String id, SponsorshipHackathonDTO sponsorshipHackathonDTO);

    void delete(Long id);

    List<SponsorshipHackathonDTO> getAll();

    SponsorshipHackathonDTO getById(String id);
}
