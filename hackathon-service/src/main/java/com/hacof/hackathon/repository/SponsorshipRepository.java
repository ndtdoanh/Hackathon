package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Sponsorship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SponsorshipRepository
        extends JpaRepository<Sponsorship, Long>, JpaSpecificationExecutor<Sponsorship> {}
