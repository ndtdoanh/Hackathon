package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.mapper.BoardListMapper;
import com.hacof.communication.repository.BoardListRepository;
import com.hacof.communication.repository.BoardRepository;
import com.hacof.communication.service.BoardListService;

@Service
public class BoardListServiceImpl implements BoardListService {

    @Autowired
    private BoardListRepository boardListRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardListMapper boardListMapper;

    @Override
    public BoardListResponseDTO createBoardList(BoardListRequestDTO boardListRequestDTO) {
        Optional<Board> boardOptional = boardRepository.findById(Long.parseLong(boardListRequestDTO.getBoardId()));
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found!");
        }

        BoardList boardList = boardListMapper.toEntity(boardListRequestDTO, boardOptional.get());
        boardList = boardListRepository.save(boardList);

        return boardListMapper.toDto(boardList);
    }

    @Override
    public BoardListResponseDTO updateBoardList(Long id, BoardListRequestDTO boardListRequestDTO) {
        Optional<BoardList> boardListOptional = boardListRepository.findById(id);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList not found!");
        }

        Optional<Board> boardOptional = boardRepository.findById(Long.parseLong(boardListRequestDTO.getBoardId()));
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found!");
        }

        BoardList boardList = boardListOptional.get();
        boardList.setName(boardListRequestDTO.getName());
        boardList.setPosition(boardListRequestDTO.getPosition());
        boardList.setBoard(boardOptional.get());
        boardList = boardListRepository.save(boardList);

        return boardListMapper.toDto(boardList);
    }

    @Override
    public void deleteBoardList(Long id) {
        Optional<BoardList> boardListOptional = boardListRepository.findById(id);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList not found!");
        }

        boardListRepository.deleteById(id);
    }

    @Override
    public BoardListResponseDTO getBoardList(Long id) {
        Optional<BoardList> boardListOptional = boardListRepository.findById(id);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList not found!");
        }

        return boardListMapper.toDto(boardListOptional.get());
    }

    @Override
    public List<BoardListResponseDTO> getAllBoardLists() {
        List<BoardList> boardLists = boardListRepository.findAll();
        return boardLists.stream().map(boardListMapper::toDto).collect(Collectors.toList());
    }
}
