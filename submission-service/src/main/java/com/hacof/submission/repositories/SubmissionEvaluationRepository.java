package com.hacof.submission.repositories;

import com.hacof.submission.entities.Submissionevaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionEvaluationRepository extends JpaRepository<Submissionevaluation, Long> {
}
