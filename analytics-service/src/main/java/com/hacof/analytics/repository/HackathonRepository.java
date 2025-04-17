package com.hacof.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.analytics.entity.Hackathon;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {}
