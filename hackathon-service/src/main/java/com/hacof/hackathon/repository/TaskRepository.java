package com.hacof.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hacof.hackathon.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {}
