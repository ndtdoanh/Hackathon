package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ScheduleEvent;

@Repository
public interface ScheduleEventRepository extends JpaRepository<ScheduleEvent, Long> {
    List<ScheduleEvent> findByScheduleId(Long scheduleId);
    boolean existsByScheduleIdAndName(Long scheduleId, String name);
    boolean existsByScheduleIdAndNameAndIdNot(Long scheduleId, String name, Long id);
}
