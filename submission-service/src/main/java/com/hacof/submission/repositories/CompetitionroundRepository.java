package com.hacof.submission.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.submission.entities.Competitionround;

@Repository
public interface CompetitionroundRepository extends JpaRepository<Competitionround, Long> {}
