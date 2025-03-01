package com.hacof.communication.repositories;

import com.hacof.communication.entities.Forumcomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumcommentRepository extends JpaRepository<Forumcomment, Long> {
    List<Forumcomment> findByThreadId(Long threadId);

}
