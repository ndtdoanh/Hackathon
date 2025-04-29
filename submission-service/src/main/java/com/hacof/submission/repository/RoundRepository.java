package com.hacof.submission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.Round;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
    Optional<Round> findByHackathonIdAndRoundNumber(Long hackathonId, Integer roundNumber);
}
