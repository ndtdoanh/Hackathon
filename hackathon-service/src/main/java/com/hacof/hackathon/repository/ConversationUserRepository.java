package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Conversation;
import com.hacof.hackathon.entity.ConversationUser;
import com.hacof.hackathon.entity.User;

public interface ConversationUserRepository extends JpaRepository<ConversationUser, Long> {
    boolean existsByConversationAndUser(Conversation conversation, User user);
}
