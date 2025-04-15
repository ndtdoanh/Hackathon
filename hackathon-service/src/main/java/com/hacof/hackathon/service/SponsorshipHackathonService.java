package com.hacof.hackathon.service;

import java.util.List;

import com.hacof.hackathon.dto.SponsorshipHackathonDTO;

public interface SponsorshipHackathonService {
    SponsorshipHackathonDTO create(SponsorshipHackathonDTO sponsorshipHackathonDTO);

    SponsorshipHackathonDTO update(String id, SponsorshipHackathonDTO sponsorshipHackathonDTO);

    void delete(Long id);

    List<SponsorshipHackathonDTO> getAll();

    SponsorshipHackathonDTO getById(String id);

    List<SponsorshipHackathonDTO> getAllByHackathonId(String hackathonId);

    List<SponsorshipHackathonDTO> getAllBySponsorshipId(String sponsorshipId);

    SponsorshipHackathonDTO getByHackathonAndSponsorshipId(String hackathonId, String sponsorshipId);
}
