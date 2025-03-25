package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ScheduleEvent;

@Repository
public interface ScheduleEventRepository extends JpaRepository<ScheduleEvent, Long> {
    // Các phương thức tìm kiếm tùy chỉnh có thể thêm vào nếu cần
}
