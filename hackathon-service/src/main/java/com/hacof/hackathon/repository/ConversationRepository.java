package com.hacof.hackathon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hacof.hackathon.entity.Conversation;
import com.hacof.hackathon.entity.Team;

import feign.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    boolean existsByTeam(Team team);

    @Query("SELECT c FROM Conversation c WHERE c.team.id = :teamId")
    Optional<Conversation> findByTeamId(@Param("teamId") Long teamId);
}
