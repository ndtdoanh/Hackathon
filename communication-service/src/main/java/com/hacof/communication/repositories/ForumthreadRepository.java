package com.hacof.communication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entities.Forumthread;

@Repository
public interface ForumthreadRepository extends JpaRepository<Forumthread, Long> {}
