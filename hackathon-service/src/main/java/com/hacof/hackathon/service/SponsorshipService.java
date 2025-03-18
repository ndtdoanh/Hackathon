package com.hacof.hackathon.service;

import com.hacof.hackathon.dto.SponsorshipDTO;

public interface SponsorshipService {
    SponsorshipDTO createSponsorship(Long hackathonId, String sponsorName);

    SponsorshipDTO approveSponsorship(Long sponsorshipId, Long adminId);

    SponsorshipDTO rejectSponsorship(Long sponsorshipId, Long adminId);
}
