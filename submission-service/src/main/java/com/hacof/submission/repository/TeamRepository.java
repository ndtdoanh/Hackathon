package com.hacof.submission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {}
