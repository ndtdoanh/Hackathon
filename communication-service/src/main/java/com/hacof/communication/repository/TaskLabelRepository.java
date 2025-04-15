package com.hacof.communication.repository;

import com.hacof.communication.entity.BoardLabel;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
    Optional<TaskLabel> findByTaskAndBoardLabel(Task task, BoardLabel boardLabel);
}
