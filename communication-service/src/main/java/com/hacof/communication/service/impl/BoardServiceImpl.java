package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.BoardRequestDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.User;
import com.hacof.communication.entity.Team;
import com.hacof.communication.mapper.BoardMapper;
import com.hacof.communication.repository.BoardRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.repository.TeamRepository;
import com.hacof.communication.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public BoardResponseDTO createBoard(BoardRequestDTO boardRequestDTO) {
        Optional<User> ownerOptional = userRepository.findById(boardRequestDTO.getOwnerId());
        if (!ownerOptional.isPresent()) {
            throw new IllegalArgumentException("Owner not found!");
        }
        User owner = ownerOptional.get();
        Optional<Team> teamOptional = teamRepository.findById(boardRequestDTO.getTeamId());
        if (!teamOptional.isPresent()) {
            throw new IllegalArgumentException("Team not found!");
        }

        Team team = teamOptional.get();
        Board board = new Board();
        board.setName(boardRequestDTO.getName());
        board.setDescription(boardRequestDTO.getDescription());
        board.setOwner(owner);
        board.setTeam(team);

        board = boardRepository.save(board);
        return new BoardResponseDTO(board.getId(), board.getName(), board.getDescription(),
                board.getOwner().getUsername(), board.getTeam().getName(),
                board.getCreatedBy().getUsername(), board.getCreatedDate(),
                board.getLastModifiedDate());
    }

    @Override
    public BoardResponseDTO updateBoard(Long id, BoardRequestDTO boardRequestDTO) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found!");
        }

        // Lấy Board từ Optional
        Board board = boardOptional.get();

        Optional<User> ownerOptional = userRepository.findById(boardRequestDTO.getOwnerId());
        if (!ownerOptional.isPresent()) {
            throw new IllegalArgumentException("Owner not found!");
        }
        User owner = ownerOptional.get();
        Optional<Team> teamOptional = teamRepository.findById(boardRequestDTO.getTeamId());
        if (!teamOptional.isPresent()) {
            throw new IllegalArgumentException("Team not found!");
        }
        Team team = teamOptional.get();

        // Cập nhật các trường của Board từ BoardRequestDTO
        board.setName(boardRequestDTO.getName());
        board.setDescription(boardRequestDTO.getDescription());
        board.setOwner(owner);
        board.setTeam(team);


        board = boardRepository.save(board);
        return new BoardResponseDTO(board.getId(), board.getName(), board.getDescription(),
                board.getOwner().getUsername(), board.getTeam().getName(),
                board.getCreatedBy().getUsername(), board.getCreatedDate(),
                board.getLastModifiedDate());
    }

    @Override
    public void deleteBoard(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found!");
        }

        boardRepository.deleteById(id);
    }

    @Override
    public BoardResponseDTO getBoard(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found!");
        }

        return boardMapper.toDto(boardOptional.get());
    }

}
