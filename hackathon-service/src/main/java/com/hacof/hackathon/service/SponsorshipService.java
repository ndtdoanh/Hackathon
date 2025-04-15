package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SponsorshipService {
    SponsorshipDTO create(SponsorshipDTO sponsorshipDTO);

    SponsorshipDTO update(String id, SponsorshipDTO sponsorshipDTO);

    void delete(Long id);

    List<SponsorshipDTO> getAll(Specification<Sponsorship> specification);
}
