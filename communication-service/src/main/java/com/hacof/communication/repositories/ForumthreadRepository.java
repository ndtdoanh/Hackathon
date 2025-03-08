package com.hacof.communication.repositories;

import com.hacof.communication.entities.Forumthread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumthreadRepository extends JpaRepository<Forumthread, Long> {
}
