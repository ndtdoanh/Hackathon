package com.hacof.communication.repository;

import com.hacof.communication.constant.ScheduleEventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ScheduleEvent;

import java.util.List;

@Repository
public interface ScheduleEventRepository extends JpaRepository<ScheduleEvent, Long> {
    List<ScheduleEvent> findByScheduleId(Long scheduleId);


}
