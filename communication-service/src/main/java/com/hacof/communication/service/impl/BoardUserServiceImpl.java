package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hacof.communication.constant.BoardUserRole;
import com.hacof.communication.dto.request.BoardUserRequestDTO;
import com.hacof.communication.dto.response.BoardUserResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardUser;
import com.hacof.communication.entity.User;
import com.hacof.communication.mapper.BoardUserMapper;
import com.hacof.communication.repository.BoardRepository;
import com.hacof.communication.repository.BoardUserRepository;
import com.hacof.communication.repository.UserRepository;
import com.hacof.communication.service.BoardUserService;

@Service
public class BoardUserServiceImpl implements BoardUserService {

    @Autowired
    private BoardUserRepository boardUserRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardUserMapper boardUserMapper;

    @Override
    public BoardUserResponseDTO createBoardUser(BoardUserRequestDTO requestDTO) {
        // Validate Board existence
        Optional<Board> boardOptional = boardRepository.findById(Long.valueOf(requestDTO.getBoardId()));
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found with ID: " + requestDTO.getBoardId());
        }

        // Validate User existence
        Optional<User> userOptional = userRepository.findById(Long.valueOf(requestDTO.getUserId()));
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + requestDTO.getUserId());
        }

        // Check if the user is already assigned to the board
        boolean alreadyAssigned = boardUserRepository.existsByBoardAndUser(boardOptional.get(), userOptional.get());
        if (alreadyAssigned) {
            throw new IllegalArgumentException("User is already assigned to this board.");
        }

        // Validate role
        try {
            BoardUserRole role = BoardUserRole.valueOf(requestDTO.getRole());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role provided: " + requestDTO.getRole());
        }

        // Create and save the new BoardUser
        BoardUser boardUser = new BoardUser();
        boardUser.setBoard(boardOptional.get());
        boardUser.setUser(userOptional.get());
        boardUser.setRole(BoardUserRole.valueOf(requestDTO.getRole()));
        boardUser.setDeleted(false);

        boardUser = boardUserRepository.save(boardUser);
        return boardUserMapper.toDto(boardUser);
    }

    @Override
    public BoardUserResponseDTO updateBoardUser(Long id, BoardUserRequestDTO requestDTO) {
        // Check if the BoardUser exists
        BoardUser boardUser = boardUserRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BoardUser not found with ID: " + id));

        // Validate Board existence
        Optional<Board> boardOptional = boardRepository.findById(Long.valueOf(requestDTO.getBoardId()));
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found with ID: " + requestDTO.getBoardId());
        }

        // Validate User existence
        Optional<User> userOptional = userRepository.findById(Long.valueOf(requestDTO.getUserId()));
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + requestDTO.getUserId());
        }

        // Validate role
        try {
            BoardUserRole role = BoardUserRole.valueOf(requestDTO.getRole());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role provided: " + requestDTO.getRole());
        }

        // Update the BoardUser entity
        boardUser.setBoard(boardOptional.get());
        boardUser.setUser(userOptional.get());
        boardUser.setRole(BoardUserRole.valueOf(requestDTO.getRole()));

        boardUser = boardUserRepository.save(boardUser);
        return boardUserMapper.toDto(boardUser);
    }

    @Override
    public BoardUserResponseDTO deleteBoardUser(Long id) {
        // Tìm BoardUser
        BoardUser boardUser = boardUserRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BoardUser not found with ID: " + id));

        if (boardUser.isDeleted()) {
            throw new IllegalArgumentException("BoardUser with ID " + id + " has already been deleted.");
        }

        String currentUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository
                .findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found: " + currentUsername));

        boardUser.setDeleted(true);
        boardUser.setDeletedBy(currentUser);
        boardUser = boardUserRepository.save(boardUser);

        // Trả về DTO
        return boardUserMapper.toDto(boardUser);
    }

    @Override
    public BoardUserResponseDTO getBoardUser(Long id) {
        BoardUser boardUser = boardUserRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("BoardUser not found with ID: " + id));

        return boardUserMapper.toDto(boardUser);
    }

    @Override
    public List<BoardUserResponseDTO> getAllBoardUsers() {
        List<BoardUser> boardUsers = boardUserRepository.findAll();
        return boardUsers.stream().map(boardUserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BoardUserResponseDTO> getBoardUsersByBoardId(Long boardId) {
        List<BoardUser> boardUsers = boardUserRepository.findByBoardId(boardId);
        return boardUsers.stream().map(boardUserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BoardUserResponseDTO> getBoardUsersByUserId(Long userId) {
        List<BoardUser> boardUsers = boardUserRepository.findByUserId(userId);
        return boardUsers.stream().map(boardUserMapper::toDto).collect(Collectors.toList());
    }
}
