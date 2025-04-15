package com.hacof.communication.repository;

import com.hacof.communication.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByTeamId(Long teamId);

    List<Schedule> findByCreatedByUsernameAndHackathonId(String createdByUsername, Long hackathonId);

    boolean existsByTeamIdAndName(Long teamId, String name);

    boolean existsByTeamIdAndNameAndIdNot(Long teamId, String name, Long id);

    List<Schedule> findByTeamIdAndHackathonId(Long teamId, Long hackathonId);
}
