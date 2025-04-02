package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTeamId(Long teamId);

    List<Schedule> findByCreatedByUsernameAndHackathonId(String createdByUsername, Long hackathonId);
}
