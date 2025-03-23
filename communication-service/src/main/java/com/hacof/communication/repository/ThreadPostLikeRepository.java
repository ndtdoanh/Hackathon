package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.communication.entity.ThreadPostLike;

public interface ThreadPostLikeRepository extends JpaRepository<ThreadPostLike, Long> {
    List<ThreadPostLike> findByThreadPostId(Long threadPostId);
}
