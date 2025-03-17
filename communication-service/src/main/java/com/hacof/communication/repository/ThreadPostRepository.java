package com.hacof.communication.repository;

import com.hacof.communication.entity.ThreadPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadPostRepository extends JpaRepository<ThreadPost, Long> {
}
