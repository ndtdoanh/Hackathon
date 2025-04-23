package com.hacof.hackathon.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hacof.hackathon.entity.Team;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {
    @Query("SELECT t FROM Team t " +
            "LEFT JOIN FETCH t.teamMembers " +
            "LEFT JOIN FETCH t.teamHackathons th " +
            "LEFT JOIN FETCH th.hackathon " +
            "WHERE th.hackathon.id = :hackathonId")
    List<Team> findByHackathonId(@Param("hackathonId") Long hackathonId);
}
