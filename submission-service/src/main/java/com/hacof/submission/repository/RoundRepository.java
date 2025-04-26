package com.hacof.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.Round;

import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    Optional<Round> findByHackathonIdAndRoundNumber(Long hackathonId, Integer roundNumber);
}
