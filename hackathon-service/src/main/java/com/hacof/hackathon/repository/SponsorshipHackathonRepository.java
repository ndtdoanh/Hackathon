package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.SponsorshipHackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SponsorshipHackathonRepository extends JpaRepository<SponsorshipHackathon, Long> {
    List<SponsorshipHackathon> findAllByHackathonId(Long hackathonId);

    List<SponsorshipHackathon> findAllBySponsorshipId(Long sponsorshipId);

    SponsorshipHackathon findByHackathonIdAndSponsorshipId(Long hackathonId, Long sponsorshipId);
}
