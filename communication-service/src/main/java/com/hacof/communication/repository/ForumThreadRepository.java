package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ForumThread;

@Repository
public interface ForumThreadRepository extends JpaRepository<ForumThread, Long> {
    boolean existsByTitleAndForumCategoryId(String title, Long forumCategoryId);
    boolean existsByTitleAndForumCategoryIdAndIdNot(String title, Long categoryId, Long id);
    List<ForumThread> findByForumCategoryId(Long forumCategoryId);
}
