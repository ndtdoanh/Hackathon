package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.Hackathon;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {}
