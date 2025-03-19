package com.hacof.communication.repository;

import com.hacof.communication.entity.ThreadPostReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadPostReportRepository extends JpaRepository<ThreadPostReport, Long> {
}
