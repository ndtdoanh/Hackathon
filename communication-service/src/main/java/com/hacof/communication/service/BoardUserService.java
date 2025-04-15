package com.hacof.communication.service;

import com.hacof.communication.dto.request.BoardUserRequestDTO;
import com.hacof.communication.dto.response.BoardUserResponseDTO;

import java.util.List;

public interface BoardUserService {

    BoardUserResponseDTO createBoardUser(BoardUserRequestDTO requestDTO);

    BoardUserResponseDTO updateBoardUser(Long id, BoardUserRequestDTO requestDTO);

    void deleteBoardUser(Long id);

    BoardUserResponseDTO getBoardUser(Long id);

    List<BoardUserResponseDTO> getAllBoardUsers();

    List<BoardUserResponseDTO> getBoardUsersByBoardId(Long boardId);

    List<BoardUserResponseDTO> getBoardUsersByUserId(Long userId);
}
