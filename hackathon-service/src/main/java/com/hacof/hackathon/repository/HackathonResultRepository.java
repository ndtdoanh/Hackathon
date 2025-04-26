package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hacof.hackathon.entity.HackathonResult;

import feign.Param;

public interface HackathonResultRepository extends JpaRepository<HackathonResult, Long> {
    @Query("SELECT r FROM HackathonResult r " + "JOIN FETCH r.team t "
            + "LEFT JOIN FETCH t.teamLeader "
            + "LEFT JOIN FETCH t.teamMembers "
            + "LEFT JOIN FETCH t.teamHackathons th "
            + "LEFT JOIN FETCH th.hackathon "
            + "WHERE r.hackathon.id = :hackathonId")
    List<HackathonResult> findDetailedByHackathonId(@Param("hackathonId") Long hackathonId);

    boolean existsByHackathonIdAndTeamId(Long hackathonId, Long teamId);
}
