package com.hacof.submission.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entities.EvaluationCriteria;

@Repository
public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Integer> {}
