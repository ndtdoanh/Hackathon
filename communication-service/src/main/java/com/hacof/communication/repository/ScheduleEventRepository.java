package com.hacof.communication.repository;

import com.hacof.communication.entity.ScheduleEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleEventRepository extends JpaRepository<ScheduleEvent, Long> {
    List<ScheduleEvent> findByScheduleId(Long scheduleId);

    boolean existsByScheduleIdAndName(Long scheduleId, String name);

    boolean existsByScheduleIdAndNameAndIdNot(Long scheduleId, String name, Long id);
}
