package com.hacof.identity.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hacof.identity.constant.Status;
import com.hacof.identity.entity.ActivityLog;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    @Query("SELECT a FROM ActivityLog a " + "WHERE (:action IS NULL OR a.action = :action) "
            + "AND (:target IS NULL OR a.target LIKE %:target%) "
            + "AND (:username IS NULL OR a.user.username = :username) "
            + "AND (:fromDate IS NULL OR a.createdDate >= :fromDate) "
            + "AND (:toDate IS NULL OR a.createdDate <= :toDate) "
            + "AND (:ipAddress IS NULL OR a.ipAddress = :ipAddress) "
            + "AND (:status IS NULL OR a.status = :status)")
    List<ActivityLog> searchLogs(
            @Param("action") String action,
            @Param("target") String target,
            @Param("username") String username,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("ipAddress") String ipAddress,
            @Param("status") Status status);
}
