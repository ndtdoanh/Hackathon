package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hacof.hackathon.entity.Round;

public interface RoundRepository extends JpaRepository<Round, Long>, JpaSpecificationExecutor<Round> {
    List<Round> findByHackathonId(Long hackathonId);
}
