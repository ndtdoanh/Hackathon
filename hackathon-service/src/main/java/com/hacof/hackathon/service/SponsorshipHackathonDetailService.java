package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.SponsorshipHackathonDetailDTO;

import java.util.List;

public interface SponsorshipHackathonDetailService {
    SponsorshipHackathonDetailDTO create(SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);

    SponsorshipHackathonDetailDTO update(Long id, SponsorshipHackathonDetailDTO sponsorshipHackathonDetailDTO);

    void delete(Long id);

    List<SponsorshipHackathonDetailDTO> getAll();

    SponsorshipHackathonDetailDTO getById(Long id);

    List<SponsorshipHackathonDetailDTO> getAllBySponsorshipHackathonId(String sponsorshipHackathonId);
}
