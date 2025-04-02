package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.HackathonResult;

@Repository
public interface HackathonResultRepository extends JpaRepository<HackathonResult, Long> {
    List<HackathonResult> findByHackathonId(Long hackathonId);
}
