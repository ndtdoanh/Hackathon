package com.hacof.communication.repository;

import com.hacof.communication.entity.ScheduleEventReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleEventReminderRepository extends JpaRepository<ScheduleEventReminder, Long> {
}
