package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByTeamAndHackathon(Team team, Hackathon hackathon);
}
