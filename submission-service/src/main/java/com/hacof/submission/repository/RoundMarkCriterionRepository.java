package com.hacof.submission.repository;

import com.hacof.submission.entity.RoundMarkCriterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoundMarkCriterionRepository extends JpaRepository<RoundMarkCriterion, Long> {

}
