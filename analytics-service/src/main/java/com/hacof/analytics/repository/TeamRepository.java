package com.hacof.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.analytics.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {}
