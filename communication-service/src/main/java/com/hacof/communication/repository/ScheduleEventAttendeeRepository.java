package com.hacof.communication.repository;

import com.hacof.communication.entity.ScheduleEventAttendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleEventAttendeeRepository extends JpaRepository<ScheduleEventAttendee, Long> {

    // Tìm tất cả những người tham gia một sự kiện theo scheduleEventId
    List<ScheduleEventAttendee> findByScheduleEventId(Long scheduleEventId);

    // Tìm một người tham gia sự kiện theo scheduleEventId và userId
    ScheduleEventAttendee findByScheduleEventIdAndUserId(Long scheduleEventId, Long userId);
}
