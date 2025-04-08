package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.TeamHackathon;

public interface TeamHackathonRepository extends JpaRepository<TeamHackathon, Long> {
    boolean existsByTeamAndHackathon(Team team, Hackathon hackathon);
}
