package com.hacof.communication.repository;

import java.util.List;

import com.hacof.communication.entity.ScheduleEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ScheduleEventAttendee;

@Repository
public interface ScheduleEventAttendeeRepository extends JpaRepository<ScheduleEventAttendee, Long> {

    // Tìm tất cả những người tham gia một sự kiện theo scheduleEventId
    List<ScheduleEventAttendee> findByScheduleEventId(Long scheduleEventId);

    boolean existsByScheduleEventIdAndUserId(Long scheduleEventId, Long userId);

    // Tìm một người tham gia sự kiện theo scheduleEventId và userId
    ScheduleEventAttendee findByScheduleEventIdAndUserId(Long scheduleEventId, Long userId);

    void deleteByScheduleEvent(ScheduleEvent scheduleEvent);
}
