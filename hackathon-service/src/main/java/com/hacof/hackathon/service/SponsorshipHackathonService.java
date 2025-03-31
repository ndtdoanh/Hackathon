package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.SponsorshipHackathonDTO;

public interface SponsorshipHackathonService {
    SponsorshipHackathonDTO create(SponsorshipHackathonDTO sponsorshipHackathonDTO);

    SponsorshipHackathonDTO update(Long id, SponsorshipHackathonDTO sponsorshipHackathonDTO);

    void delete(Long id);

    List<SponsorshipHackathonDTO> getAll();
}
