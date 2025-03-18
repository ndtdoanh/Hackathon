package com.hacof.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.RoundMarkCriterion;

@Repository
public interface RoundMarkCriterionRepository extends JpaRepository<RoundMarkCriterion, Long> {}
