package com.hacof.communication.repository;

import com.hacof.communication.entity.MessageReaction;
import com.hacof.communication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageReactionRepository extends JpaRepository<MessageReaction, Long> {
    List<MessageReaction> findByMessageId(Long messageId);

    Optional<MessageReaction> findByMessageIdAndCreatedBy(Long messageId, User createdBy);
}
