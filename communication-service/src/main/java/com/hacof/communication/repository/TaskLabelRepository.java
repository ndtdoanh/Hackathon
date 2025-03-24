package com.hacof.communication.repository;

import com.hacof.communication.entity.TaskLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
}
