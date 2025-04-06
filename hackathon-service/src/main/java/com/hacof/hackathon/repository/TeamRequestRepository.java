package com.hacof.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.TeamRequest;

import feign.Param;

@Repository
public interface TeamRequestRepository extends JpaRepository<TeamRequest, Long>, JpaSpecificationExecutor<TeamRequest> {
    @Query(
            "SELECT tr FROM TeamRequest tr JOIN tr.teamRequestMembers trm WHERE tr.hackathon.id = :hackathonId AND trm.user.id = :userId")
    List<TeamRequest> findAllByHackathonIdAndUserId(
            @Param("hackathonId") Long hackathonId, @Param("userId") Long userId);

    @Query("SELECT tr FROM TeamRequest tr JOIN tr.teamRequestMembers trm WHERE trm.user.id = :userId")
    List<TeamRequest> findAllByUserId(@Param("userId") Long userId);

    List<TeamRequest> findAllByHackathonId(Long hackathonId);

    @Query(
            "SELECT tr FROM TeamRequest tr JOIN tr.teamRequestMembers trm WHERE tr.hackathon.id = :hackathonId AND trm.user.id = :memberId")
    List<TeamRequest> findByMemberIdAndHackathonId(
            @Param("memberId") Long memberId, @Param("hackathonId") Long hackathonId);
}
