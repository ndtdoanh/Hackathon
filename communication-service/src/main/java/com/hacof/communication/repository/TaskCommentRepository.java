package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.TaskComment;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {}
