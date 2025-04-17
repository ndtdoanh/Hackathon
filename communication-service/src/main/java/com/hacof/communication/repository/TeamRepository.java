package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {}
