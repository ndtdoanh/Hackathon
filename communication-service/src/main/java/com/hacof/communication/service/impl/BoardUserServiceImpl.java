package com.hacof.communication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    public BoardUserResponseDTO createBoardUser(BoardUserRequestDTO requestDTO) {
        Board board = boardRepository.findById(requestDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean alreadyAssigned = boardUserRepository.existsByBoardAndUser(board, user);
        if (alreadyAssigned) {
            throw new IllegalArgumentException("User is already assigned to this board.");
        }

        BoardUser boardUser = new BoardUser();
        boardUser.setBoard(board);
        boardUser.setUser(user);
        boardUser.setRole(BoardUserRole.valueOf(requestDTO.getRole()));  // Ensure valid role
        boardUser.setDeleted(false);

        boardUser = boardUserRepository.save(boardUser);
        return boardUserMapper.toDto(boardUser);
    }


    @Override
    public BoardUserResponseDTO updateBoardUser(Long id, BoardUserRequestDTO requestDTO) {
        BoardUser boardUser =
                boardUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("BoardUser not found"));

        Board board = boardRepository
                .findById(requestDTO.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        User user = userRepository
                .findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boardUser.setBoard(board);
        boardUser.setUser(user);
        boardUser.setRole(BoardUserRole.valueOf(requestDTO.getRole()));  // Update the role

        boardUser = boardUserRepository.save(boardUser);
        return boardUserMapper.toDto(boardUser);
    }

    @Override
    public void deleteBoardUser(Long id) {
        BoardUser boardUser =
                boardUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("BoardUser not found"));

        boardUser.setDeleted(true);

        boardUserRepository.save(boardUser);
    }

    @Override
    public BoardUserResponseDTO getBoardUser(Long id) {
        BoardUser boardUser =
                boardUserRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("BoardUser not found"));
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
