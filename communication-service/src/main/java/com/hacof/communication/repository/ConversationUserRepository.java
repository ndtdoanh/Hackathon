package com.hacof.communication.repository;

import com.hacof.communication.entity.ConversationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationUserRepository extends JpaRepository<ConversationUser, Long> {}
