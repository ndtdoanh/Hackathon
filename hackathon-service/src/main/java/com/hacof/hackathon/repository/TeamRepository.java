package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {}
