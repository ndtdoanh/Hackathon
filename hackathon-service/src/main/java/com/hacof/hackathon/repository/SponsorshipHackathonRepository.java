package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.SponsorshipHackathon;

@Repository
public interface SponsorshipHackathonRepository extends JpaRepository<SponsorshipHackathon, Long> {
    List<SponsorshipHackathon> findAllByHackathonId(Long hackathonId);

    List<SponsorshipHackathon> findAllBySponsorshipId(Long sponsorshipId);

    SponsorshipHackathon findByHackathonIdAndSponsorshipId(Long hackathonId, Long sponsorshipId);
}
