package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.SponsorshipHackathonDetail;

@Repository
public interface SponsorshipHackathonDetailRepository extends JpaRepository<SponsorshipHackathonDetail, Long> {
    List<SponsorshipHackathonDetail> findAllBySponsorshipHackathonId(Long sponsorshipHackathonId);
}
