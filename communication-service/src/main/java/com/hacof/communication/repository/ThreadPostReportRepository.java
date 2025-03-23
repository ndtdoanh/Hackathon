package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.communication.entity.ThreadPostReport;

public interface ThreadPostReportRepository extends JpaRepository<ThreadPostReport, Long> {
    List<ThreadPostReport> findByThreadPostId(Long threadPostId);
}
