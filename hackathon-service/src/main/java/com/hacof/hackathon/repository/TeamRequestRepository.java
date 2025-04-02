package com.hacof.hackathon.repository;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hacof.hackathon.entity.TeamRequest;

import java.util.List;

@Repository
public interface TeamRequestRepository extends JpaRepository<TeamRequest, Long>, JpaSpecificationExecutor<TeamRequest> {
    @Query("SELECT tr FROM TeamRequest tr JOIN tr.teamRequestMembers trm WHERE tr.hackathon.id = :hackathonId AND trm.user.id = :userId")
    List<TeamRequest> findAllByHackathonIdAndUserId(@Param("hackathonId") Long hackathonId, @Param("userId") Long userId);

    @Query("SELECT tr FROM TeamRequest tr JOIN tr.teamRequestMembers trm WHERE trm.user.id = :userId")
    List<TeamRequest> findAllByUserId(@Param("userId") Long userId);
    List<TeamRequest> findAllByHackathonId(Long hackathonId);
}
