package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ScheduleEventReminder;

import java.util.List;

@Repository
public interface ScheduleEventReminderRepository extends JpaRepository<ScheduleEventReminder, Long> {

    List<ScheduleEventReminder> findByScheduleEventId(Long scheduleEventId);

    List<ScheduleEventReminder> findByUserId(Long userId);
}
