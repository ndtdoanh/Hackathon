package com.hacof.submission.repositories;

import com.hacof.submission.entities.Competitionround;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionroundRepository extends JpaRepository<Competitionround, Long> {
}
