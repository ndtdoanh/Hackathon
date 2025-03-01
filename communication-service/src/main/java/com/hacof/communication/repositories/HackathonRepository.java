package com.hacof.communication.repositories;

import com.hacof.communication.entities.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
}
