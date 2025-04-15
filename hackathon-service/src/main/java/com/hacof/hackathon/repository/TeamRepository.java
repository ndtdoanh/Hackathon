package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team> {
    //    @Query(
    //            "SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Team t JOIN t.teamMembers m WHERE
    // t.teamHackathons = :hackathonId AND m.user.id = :userId")
    //    boolean existsByHackathonIdAndMemberId(@Param("hackathonId") Long hackathonId, @Param("userId") String
    // userId);
}
