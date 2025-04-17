package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTeamId(Long teamId);

    List<Schedule> findByCreatedByUsernameAndHackathonId(String createdByUsername, Long hackathonId);

    boolean existsByTeamIdAndName(Long teamId, String name);

    boolean existsByTeamIdAndNameAndIdNot(Long teamId, String name, Long id);

    List<Schedule> findByTeamIdAndHackathonId(Long teamId, Long hackathonId);
}
