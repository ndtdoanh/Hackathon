package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;

public interface SponsorshipHackathonDetailService {
    SponsorshipHackathonDetailDTO create(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);

    SponsorshipHackathonDetailDTO update(Long id, SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);

    void delete(Long id);

    List<SponsorshipHackathonDetailDTO> getAll();

    SponsorshipHackathonDetailDTO getById(Long id);
}
