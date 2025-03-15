package com.hacof.submission.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entities.EvaluationScores;

@Repository
public interface EvaluationScoreRepository extends JpaRepository<EvaluationScores, Integer> {
    List<EvaluationScores> findByDeletedAtIsNull();

    List<EvaluationScores> findBySubmissionId(Long submissionId);
}
