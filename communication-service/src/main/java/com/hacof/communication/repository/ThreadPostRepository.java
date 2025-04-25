package com.hacof.communication.repository;

import com.hacof.communication.entity.ForumThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ThreadPost;

import java.util.List;

@Repository
public interface ThreadPostRepository extends JpaRepository<ThreadPost, Long> {
    List<ThreadPost> findByForumThread(ForumThread forumThread);
}
