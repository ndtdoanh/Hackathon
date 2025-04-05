package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {}
