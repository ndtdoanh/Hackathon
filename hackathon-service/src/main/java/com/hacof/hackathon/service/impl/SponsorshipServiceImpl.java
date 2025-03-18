package com.hacof.hackathon.service.impl;

import org.springframework.stereotype.Service;

import com.hacof.hackathon.constant.SponsorshipStatus;
import com.hacof.hackathon.dto.SponsorshipDTO;
import com.hacof.hackathon.entity.Sponsorship;
import com.hacof.hackathon.exception.ResourceNotFoundException;
import com.hacof.hackathon.mapper.SponsorshipMapper;
import com.hacof.hackathon.repository.SponsorshipRepository;
import com.hacof.hackathon.service.SponsorshipService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SponsorshipServiceImpl implements SponsorshipService {
    private final SponsorshipRepository sponsorshipRepository;
    private final SponsorshipMapper sponsorshipMapper;

    @Override
    public SponsorshipDTO createSponsorship(Long hackathonId, String sponsorName) {
        Sponsorship sponsorship = new Sponsorship();
        // Set hackathon and sponsor name
        sponsorship = sponsorshipRepository.save(sponsorship);
        return sponsorshipMapper.toDTO(sponsorship);
    }

    @Override
    public SponsorshipDTO approveSponsorship(Long sponsorshipId, Long adminId) {
        Sponsorship sponsorship = sponsorshipRepository
                .findById(sponsorshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"));
        sponsorship.setStatus(SponsorshipStatus.ACTIVE);
        sponsorship = sponsorshipRepository.save(sponsorship);
        return sponsorshipMapper.toDTO(sponsorship);
    }

    @Override
    public SponsorshipDTO rejectSponsorship(Long sponsorshipId, Long adminId) {
        Sponsorship sponsorship = sponsorshipRepository
                .findById(sponsorshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship not found"));
        sponsorship.setStatus(SponsorshipStatus.CANCELLED);
        sponsorship = sponsorshipRepository.save(sponsorship);
        return sponsorshipMapper.toDTO(sponsorship);
    }
}
