package com.hacof.communication.service;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.request.BulkBoardListUpdateRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;

import java.util.List;

public interface BoardListService {

    BoardListResponseDTO createBoardList(BoardListRequestDTO boardListRequestDTO);

    BoardListResponseDTO updateBoardList(Long id, BoardListRequestDTO boardListRequestDTO);

    void deleteBoardList(Long id);

    BoardListResponseDTO getBoardList(Long id);

    List<BoardListResponseDTO> getAllBoardLists();

    List<BoardListResponseDTO> updateBulkBoardLists(List<BulkBoardListUpdateRequestDTO> bulkUpdateRequest);

    List<BoardListResponseDTO> getBoardListByBoardId(Long boardId);
}
