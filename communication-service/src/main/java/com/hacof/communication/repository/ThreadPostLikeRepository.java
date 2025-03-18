package com.hacof.communication.repository;

import com.hacof.communication.entity.ThreadPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadPostLikeRepository extends JpaRepository<ThreadPostLike, Long> {
    List<ThreadPostLike> findByThreadPostId(Long threadPostId);
}
