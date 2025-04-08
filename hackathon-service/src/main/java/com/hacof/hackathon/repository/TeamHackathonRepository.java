package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.HackathonResult;
import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.TeamHackathon;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamHackathonRepository extends JpaRepository<TeamHackathon, Long> {
    boolean existsByTeamAndHackathon(Team team, Hackathon hackathon);
}
