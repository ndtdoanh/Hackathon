package com.hacof.communication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entities.Forumcomment;

@Repository
public interface ForumcommentRepository extends JpaRepository<Forumcomment, Long> {
    List<Forumcomment> findByThreadId(Long threadId);
}
