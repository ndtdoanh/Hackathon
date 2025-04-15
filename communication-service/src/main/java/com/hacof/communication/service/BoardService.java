package com.hacof.communication.service;

import com.hacof.communication.dto.request.BoardRequestDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;

import java.util.List;

public interface BoardService {
    BoardResponseDTO createBoard(BoardRequestDTO boardRequestDTO);

    BoardResponseDTO updateBoard(Long id, BoardRequestDTO boardRequestDTO);

    void deleteBoard(Long id);

    BoardResponseDTO getBoard(Long id);

    List<BoardResponseDTO> getAllBoards();

    List<BoardResponseDTO> getBoardsByTeamAndHackathon(Long teamId, Long hackathonId);
}
