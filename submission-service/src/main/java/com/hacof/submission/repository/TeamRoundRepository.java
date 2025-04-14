package com.hacof.submission.repository;

import com.hacof.submission.entity.TeamRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRoundRepository extends JpaRepository<TeamRound, Long> {
    Optional<TeamRound> findById(Long id);
}
