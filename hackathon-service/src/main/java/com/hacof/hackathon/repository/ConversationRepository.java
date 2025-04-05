package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {}
