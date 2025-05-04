package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hacof.hackathon.entity.Sponsorship;

public interface SponsorshipRepository
        extends JpaRepository<Sponsorship, Long>, JpaSpecificationExecutor<Sponsorship> {}
