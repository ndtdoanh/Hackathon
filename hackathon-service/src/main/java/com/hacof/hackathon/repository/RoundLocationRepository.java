package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.RoundLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundLocationRepository extends JpaRepository<RoundLocation, Long> {
    List<RoundLocation> findByLocationId(Long locationId);

    List<RoundLocation> findByRoundId(Long roundId);

    void deleteByRoundId(Long roundId);
}
