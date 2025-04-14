package com.hacof.submission.repository;

import com.hacof.submission.entity.Round;
import com.hacof.submission.entity.RoundMarkCriterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundMarkCriterionRepository extends JpaRepository<RoundMarkCriterion, Long> {
    @Query("SELECT rmc.id FROM RoundMarkCriterion rmc WHERE rmc.round.id = :roundId")
    List<Long> findCriterionIdsByRound(Long roundId);

    List<RoundMarkCriterion> findByRound(Round round);
}
