package com.hacof.submission.repositories;

import com.hacof.submission.entities.EvaluationScores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvaluationScoreRepository extends JpaRepository<EvaluationScores, Integer> {
    List<EvaluationScores> findByDeletedAtIsNull();

    List<EvaluationScores> findBySubmissionId(Long submissionId);
}
