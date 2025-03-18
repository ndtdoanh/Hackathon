package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hacof.hackathon.entity.RoundLocation;

public interface RoundLocationRepository
        extends JpaRepository<RoundLocation, Long>, JpaSpecificationExecutor<RoundLocation> {}
