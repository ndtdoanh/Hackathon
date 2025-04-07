package com.hacof.hackathon.repository;

import com.hacof.hackathon.entity.Conversation;
import com.hacof.hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.ConversationUser;

public interface ConversationUserRepository extends JpaRepository<ConversationUser, Long> {
    boolean existsByConversationAndUser(Conversation conversation, User user);
}
