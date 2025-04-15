package com.hacof.communication.repository;

import com.hacof.communication.entity.ForumThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumThreadRepository extends JpaRepository<ForumThread, Long> {
    boolean existsByTitleAndForumCategoryId(String title, Long forumCategoryId);

    List<ForumThread> findByForumCategoryId(Long forumCategoryId);
}
