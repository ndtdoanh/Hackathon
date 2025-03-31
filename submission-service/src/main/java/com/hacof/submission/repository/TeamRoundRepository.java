package com.hacof.submission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.TeamRound;

@Repository
public interface TeamRoundRepository extends JpaRepository<TeamRound, Long> {
    Optional<TeamRound> findById(Long id);
}
