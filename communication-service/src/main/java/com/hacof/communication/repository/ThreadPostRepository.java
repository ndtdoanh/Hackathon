package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ThreadPost;

@Repository
public interface ThreadPostRepository extends JpaRepository<ThreadPost, Long> {}
