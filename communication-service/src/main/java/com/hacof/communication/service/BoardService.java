package com.hacof.communication.service;

import java.util.List;

import com.hacof.communication.dto.request.BoardRequestDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;

public interface BoardService {
    BoardResponseDTO createBoard(BoardRequestDTO boardRequestDTO);

    BoardResponseDTO updateBoard(Long id, BoardRequestDTO boardRequestDTO);

    void deleteBoard(Long id);

    BoardResponseDTO getBoard(Long id);

    List<BoardResponseDTO> getAllBoards();

    List<BoardResponseDTO> getBoardsByTeamAndHackathon(Long teamId, Long hackathonId);
}
