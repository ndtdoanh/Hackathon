package com.hacof.submission.repository;

import com.hacof.submission.entity.JudgeRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JudgeRoundRepository extends JpaRepository<JudgeRound, Long> {
}
