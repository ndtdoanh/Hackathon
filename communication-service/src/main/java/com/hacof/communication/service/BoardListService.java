package com.hacof.communication.service;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;

import java.util.List;

public interface BoardListService {

    BoardListResponseDTO createBoardList(BoardListRequestDTO boardListRequestDTO);

    BoardListResponseDTO updateBoardList(Long id, BoardListRequestDTO boardListRequestDTO);

    void deleteBoardList(Long id);

}
