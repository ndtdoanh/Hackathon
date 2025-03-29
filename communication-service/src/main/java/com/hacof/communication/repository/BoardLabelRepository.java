package com.hacof.communication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hacof.communication.entity.BoardLabel;

@Repository
public interface BoardLabelRepository extends JpaRepository<BoardLabel, Long> {

    // Tìm tất cả các BoardLabel theo boardId
    List<BoardLabel> findByBoardId(Long boardId);
}
