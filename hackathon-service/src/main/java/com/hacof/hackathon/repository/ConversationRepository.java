package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Team;
import com.hacof.hackathon.entity.User;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Conversation;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    boolean existsByTeam(Team team);

    @Query("SELECT c FROM Conversation c WHERE c.team.id = :teamId")
    Optional<Conversation> findByTeamId(@Param("teamId") Long teamId);
}
