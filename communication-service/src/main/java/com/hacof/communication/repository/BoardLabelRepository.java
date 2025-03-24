package com.hacof.communication.repository;

import com.hacof.communication.entity.BoardLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardLabelRepository extends JpaRepository<BoardLabel, Long> {

    // Tìm tất cả các BoardLabel theo boardId
    List<BoardLabel> findByBoardId(Long boardId);
}
