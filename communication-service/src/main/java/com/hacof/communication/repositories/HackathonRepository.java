package com.hacof.communication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entities.Hackathon;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {}
