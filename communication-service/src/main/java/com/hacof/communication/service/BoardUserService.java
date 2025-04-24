package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.BoardUserRequestDTO;
import com.hacof.communication.dto.response.BoardUserResponseDTO;

public interface BoardUserService {

    BoardUserResponseDTO createBoardUser(BoardUserRequestDTO requestDTO);

    BoardUserResponseDTO updateBoardUser(Long id, BoardUserRequestDTO requestDTO);

    BoardUserResponseDTO deleteBoardUser(Long id);

    BoardUserResponseDTO undeleteBoardUser(Long id);

    BoardUserResponseDTO getBoardUser(Long id);

    List<BoardUserResponseDTO> getAllBoardUsers();

    List<BoardUserResponseDTO> getBoardUsersByBoardId(Long boardId);

    List<BoardUserResponseDTO> getBoardUsersByUserId(Long userId);
}
