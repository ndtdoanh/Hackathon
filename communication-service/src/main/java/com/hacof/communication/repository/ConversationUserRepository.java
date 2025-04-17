package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ConversationUser;

@Repository
public interface ConversationUserRepository extends JpaRepository<ConversationUser, Long> {}
