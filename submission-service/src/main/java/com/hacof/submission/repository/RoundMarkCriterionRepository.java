package com.hacof.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.RoundMarkCriterion;

import java.util.List;

@Repository
public interface RoundMarkCriterionRepository extends JpaRepository<RoundMarkCriterion, Long> {
    @Query("SELECT rmc.id FROM RoundMarkCriterion rmc WHERE rmc.round.id = :roundId")
    List<Long> findCriterionIdsByRound(Long roundId);
}
