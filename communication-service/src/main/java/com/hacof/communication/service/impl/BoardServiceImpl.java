package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.BoardRequestDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.Team;
import com.hacof.communication.entity.User;
import com.hacof.communication.entity.Hackathon;
import com.hacof.communication.mapper.BoardMapper;
import com.hacof.communication.repository.BoardRepository;
import com.hacof.communication.repository.TeamRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.repository.HackathonRepository;
import com.hacof.communication.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private HackathonRepository hackathonRepository;  // Added Hackathon Repository

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public BoardResponseDTO createBoard(BoardRequestDTO boardRequestDTO) {
        // Validate Owner
        Optional<User> ownerOptional = userRepository.findById(Long.parseLong(boardRequestDTO.getOwnerId()));
        if (!ownerOptional.isPresent()) {
            throw new IllegalArgumentException("Owner with ID " + boardRequestDTO.getOwnerId() + " not found!");
        }

        // Validate Team
        Optional<Team> teamOptional = teamRepository.findById(Long.parseLong(boardRequestDTO.getTeamId()));
        if (!teamOptional.isPresent()) {
            throw new IllegalArgumentException("Team with ID " + boardRequestDTO.getTeamId() + " not found!");
        }

        // Validate Hackathon
        Optional<Hackathon> hackathonOptional = hackathonRepository.findById(Long.parseLong(boardRequestDTO.getHackathonId()));
        if (!hackathonOptional.isPresent()) {
            throw new IllegalArgumentException("Hackathon with ID " + boardRequestDTO.getHackathonId() + " not found!");
        }

        // Check for existing board with the same name in the same team
        Optional<Board> existingBoard = boardRepository.findByNameAndTeamId(boardRequestDTO.getName(), Long.parseLong(boardRequestDTO.getTeamId()));
        if (existingBoard.isPresent()) {
            throw new IllegalArgumentException("A Board with the same name already exists for this team.");
        }

        // Validate Name and Description
        if (boardRequestDTO.getName() == null || boardRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Board name cannot be empty");
        }

        if (boardRequestDTO.getDescription() == null || boardRequestDTO.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Board description cannot be empty");
        }

        // Create the new board
        User owner = ownerOptional.get();
        Team team = teamOptional.get();
        Hackathon hackathon = hackathonOptional.get();

        Board board = new Board();
        board.setName(boardRequestDTO.getName());
        board.setDescription(boardRequestDTO.getDescription());
        board.setOwner(owner);
        board.setTeam(team);
        board.setHackathon(hackathon);

        board = boardRepository.save(board);
        return new BoardResponseDTO(
                String.valueOf(board.getId()),
                board.getName(),
                board.getDescription(),
                board.getOwner().getUsername(),
                String.valueOf(board.getTeam().getId()),
                String.valueOf(board.getHackathon().getId()),
                board.getCreatedBy().getUsername(),
                board.getCreatedDate(),
                board.getLastModifiedDate());
    }

    @Override
    public BoardResponseDTO updateBoard(Long id, BoardRequestDTO boardRequestDTO) {
        // Validate board existence
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board with ID " + id + " not found!");
        }

        // Validate owner existence
        Optional<User> ownerOptional = userRepository.findById(Long.parseLong(boardRequestDTO.getOwnerId()));
        if (!ownerOptional.isPresent()) {
            throw new IllegalArgumentException("Owner with ID " + boardRequestDTO.getOwnerId() + " not found!");
        }

        // Validate team existence
        Optional<Team> teamOptional = teamRepository.findById(Long.parseLong(boardRequestDTO.getTeamId()));
        if (!teamOptional.isPresent()) {
            throw new IllegalArgumentException("Team with ID " + boardRequestDTO.getTeamId() + " not found!");
        }

        // Validate hackathon existence
        Optional<Hackathon> hackathonOptional = hackathonRepository.findById(Long.parseLong(boardRequestDTO.getHackathonId()));
        if (!hackathonOptional.isPresent()) {
            throw new IllegalArgumentException("Hackathon with ID " + boardRequestDTO.getHackathonId() + " not found!");
        }

        // Check for duplicate board name in the same team, except for the current board being updated
        Optional<Board> existingBoard = boardRepository.findByNameAndTeamId(boardRequestDTO.getName(), Long.parseLong(boardRequestDTO.getTeamId()));
        if (existingBoard.isPresent() && !Long.valueOf(existingBoard.get().getId()).equals(Long.valueOf(id))) {
            throw new IllegalArgumentException("A Board with the same name already exists for this team.");
        }

        // Validate name and description
        if (boardRequestDTO.getName() == null || boardRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Board name cannot be empty");
        }

        if (boardRequestDTO.getDescription() == null || boardRequestDTO.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Board description cannot be empty");
        }

        // Update board
        Board board = boardOptional.get();
        User owner = ownerOptional.get();
        Team team = teamOptional.get();
        Hackathon hackathon = hackathonOptional.get();

        board.setName(boardRequestDTO.getName());
        board.setDescription(boardRequestDTO.getDescription());
        board.setOwner(owner);
        board.setTeam(team);
        board.setHackathon(hackathon);

        board = boardRepository.save(board);
        return new BoardResponseDTO(
                String.valueOf(board.getId()),
                board.getName(),
                board.getDescription(),
                board.getOwner().getUsername(),
                String.valueOf(board.getTeam().getId()),
                String.valueOf(board.getHackathon().getId()),
                board.getCreatedBy().getUsername(),
                board.getCreatedDate(),
                board.getLastModifiedDate());
    }

    @Override
    public void deleteBoard(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board with ID " + id + " not found!");
        }

        boardRepository.deleteById(id);
    }

    @Override
    public BoardResponseDTO getBoard(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board with ID " + id + " not found!");
        }

        return boardMapper.toDto(boardOptional.get());
    }

    @Override
    public List<BoardResponseDTO> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(boardMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BoardResponseDTO> getBoardsByTeamAndHackathon(Long teamId, Long hackathonId) {
        List<Board> boards = boardRepository.findByTeamIdAndHackathonId(teamId, hackathonId);
        return boards.stream().map(boardMapper::toDto).collect(Collectors.toList());
    }

}

