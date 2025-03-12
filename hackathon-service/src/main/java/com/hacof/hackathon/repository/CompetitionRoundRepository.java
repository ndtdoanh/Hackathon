package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hacof.hackathon.entity.CompetitionRound;

public interface CompetitionRoundRepository
        extends JpaRepository<CompetitionRound, Long>, JpaSpecificationExecutor<CompetitionRound> {}
