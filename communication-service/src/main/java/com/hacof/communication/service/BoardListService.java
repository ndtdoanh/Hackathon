package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;

public interface BoardListService {

    BoardListResponseDTO createBoardList(BoardListRequestDTO boardListRequestDTO);

    BoardListResponseDTO updateBoardList(Long id, BoardListRequestDTO boardListRequestDTO);

    void deleteBoardList(Long id);

    BoardListResponseDTO getBoardList(Long id);

    List<BoardListResponseDTO> getAllBoardLists();
}
