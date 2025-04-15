package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Hackathon;
import com.hacof.hackathon.entity.Schedule;
import com.hacof.hackathon.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByTeamAndHackathon(Team team, Hackathon hackathon);
}
