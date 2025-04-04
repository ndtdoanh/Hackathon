package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.ForumThread;

@Repository
public interface ForumThreadRepository extends JpaRepository<ForumThread, Long> {}
