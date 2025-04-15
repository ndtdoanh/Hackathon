package com.hacof.communication.repository;

import com.hacof.communication.entity.Task;
import com.hacof.communication.entity.TaskAssignee;
import com.hacof.communication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAssigneeRepository extends JpaRepository<TaskAssignee, Long> {
    List<TaskAssignee> findByTaskId(Long taskId);

    boolean existsByTaskAndUser(Task task, User user);

    Optional<TaskAssignee> findByTaskAndUser(Task task, User user);
}
