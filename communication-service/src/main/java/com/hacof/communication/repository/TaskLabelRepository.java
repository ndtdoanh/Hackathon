package com.hacof.communication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.BoardLabel;
import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskLabel;

@Repository
public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
    Optional<TaskLabel> findByTaskAndBoardLabel(Task task, BoardLabel boardLabel);

    List<TaskLabel> findByTaskId(Long taskId);

    void deleteAllByBoardLabelId(Long boardLabelId);
}
