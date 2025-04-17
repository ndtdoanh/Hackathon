package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT DISTINCT c FROM Conversation c " + "JOIN c.conversationUsers cu " + "WHERE cu.user.id = :userId")
    List<Conversation> findConversationsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(c) > 0 " + "FROM Conversation c JOIN c.conversationUsers cu1 JOIN c.conversationUsers cu2 "
            + "WHERE cu1.user.id = :userId1 AND cu2.user.id = :userId2 AND c.type = 'PRIVATE'")
    boolean existsSingleConversationBetweenUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
