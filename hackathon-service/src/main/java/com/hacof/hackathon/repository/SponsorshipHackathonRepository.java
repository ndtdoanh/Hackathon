package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.SponsorshipHackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorshipHackathonRepository extends JpaRepository<SponsorshipHackathon, Long> {}
