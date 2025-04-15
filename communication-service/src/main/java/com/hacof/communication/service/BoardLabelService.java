package com.hacof.communication.service;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;

import java.util.List;

public interface BoardLabelService {

    BoardLabelResponseDTO createBoardLabel(BoardLabelRequestDTO boardLabelRequestDTO);

    BoardLabelResponseDTO updateBoardLabel(Long id, BoardLabelRequestDTO boardLabelRequestDTO);

    void deleteBoardLabel(Long id);

    BoardLabelResponseDTO getBoardLabel(Long id);

    List<BoardLabelResponseDTO> getAllBoardLabels();

    List<BoardLabelResponseDTO> getBoardLabelsByBoardId(Long boardId);
}
