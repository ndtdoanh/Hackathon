package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {}
