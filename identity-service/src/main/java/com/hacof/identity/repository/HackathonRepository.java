package com.hacof.identity.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.identity.constant.Status;
import com.hacof.identity.entity.Hackathon;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    List<Hackathon> findByStatusAndEndDateBefore(Status status, LocalDateTime time);
}
