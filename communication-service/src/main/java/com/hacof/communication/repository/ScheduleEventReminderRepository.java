package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ScheduleEventReminder;

@Repository
public interface ScheduleEventReminderRepository extends JpaRepository<ScheduleEventReminder, Long> {

    List<ScheduleEventReminder> findByScheduleEventId(Long scheduleEventId);

    List<ScheduleEventReminder> findByUserId(Long userId);
}
