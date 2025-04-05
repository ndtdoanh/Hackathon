package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.ConversationUser;

public interface ConversationUserRepository extends JpaRepository<ConversationUser, Long> {}
