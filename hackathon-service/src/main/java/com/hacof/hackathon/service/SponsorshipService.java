package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.SponsorshipDTO;

public interface SponsorshipService {
    SponsorshipDTO create(SponsorshipDTO sponsorshipDTO);

    SponsorshipDTO update(Long id, SponsorshipDTO sponsorshipDTO);

    void delete(Long id);

    List<SponsorshipDTO> getAll();

    SponsorshipDTO getById(Long id);
}
