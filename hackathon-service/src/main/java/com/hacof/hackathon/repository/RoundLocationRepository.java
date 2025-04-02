package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.RoundLocation;

public interface RoundLocationRepository extends JpaRepository<RoundLocation, Long> {
    List<RoundLocation> findByLocationId(Long locationId);

    List<RoundLocation> findByRoundId(Long roundId);
}
