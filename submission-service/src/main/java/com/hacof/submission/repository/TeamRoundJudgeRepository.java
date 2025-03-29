package com.hacof.submission.repository;

import com.hacof.submission.entity.TeamRoundJudge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRoundJudgeRepository extends JpaRepository<TeamRoundJudge, Long> {
}
