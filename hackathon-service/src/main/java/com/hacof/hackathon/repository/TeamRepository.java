package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hacof.hackathon.entity.Team;

import feign.Param;

public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {
    @Query("SELECT t FROM Team t " + "LEFT JOIN FETCH t.teamMembers "
            + "LEFT JOIN FETCH t.teamHackathons th "
            + "LEFT JOIN FETCH th.hackathon "
            + "WHERE th.hackathon.id = :hackathonId")
    List<Team> findByHackathonId(@Param("hackathonId") Long hackathonId);

    boolean existsByName(String name);
}
