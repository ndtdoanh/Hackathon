package com.hacof.communication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.TaskLabel;

@Repository
public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {}
