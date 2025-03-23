package com.hacof.communication.repository;

import com.hacof.communication.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Tìm tất cả các Task theo BoardList ID
    List<Task> findByBoardListId(Long boardListId);

    // Tìm tất cả các Task theo tên
    List<Task> findByTitle(String title);
}
