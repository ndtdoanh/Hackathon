package com.hacof.communication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {}
