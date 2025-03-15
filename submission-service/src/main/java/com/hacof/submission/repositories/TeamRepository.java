package com.hacof.submission.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entities.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {}
