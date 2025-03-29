package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hacof.hackathon.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {
    List<Team> findByHackathonId(long hackathonId);
}
