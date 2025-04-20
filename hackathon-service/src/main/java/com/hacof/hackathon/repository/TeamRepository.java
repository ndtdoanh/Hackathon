package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hacof.hackathon.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {}
