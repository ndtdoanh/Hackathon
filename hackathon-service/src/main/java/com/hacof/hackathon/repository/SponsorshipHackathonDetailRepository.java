package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.SponsorshipHackathonDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorshipHackathonDetailRepository extends JpaRepository<SponsorshipHackathonDetail, Long> {}
