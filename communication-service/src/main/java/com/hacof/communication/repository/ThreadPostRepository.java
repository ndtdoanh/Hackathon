package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ForumThread;
import com.hacof.communication.entity.ThreadPost;

@Repository
public interface ThreadPostRepository extends JpaRepository<ThreadPost, Long> {
    List<ThreadPost> findByForumThread(ForumThread forumThread);
}
