package com.hacof.hackathon.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;

public interface SponsorshipService {
    SponsorshipDTO create(SponsorshipDTO sponsorshipDTO);

    SponsorshipDTO update(String id, SponsorshipDTO sponsorshipDTO);

    void delete(Long id);

    List<SponsorshipDTO> getAll(Specification<Sponsorship> specification);
}
