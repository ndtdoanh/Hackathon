package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.SponsorshipHackathonDetail;

public interface SponsorshipHackathonDetailRepository extends JpaRepository<SponsorshipHackathonDetail, Long> {
    List<SponsorshipHackathonDetail> findAllBySponsorshipHackathonId(Long sponsorshipHackathonId);
}
