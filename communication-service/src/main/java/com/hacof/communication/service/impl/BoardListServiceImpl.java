package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardList;
import com.hacof.communication.mapper.BoardListMapper;
import com.hacof.communication.repository.BoardListRepository;
import com.hacof.communication.repository.BoardRepository;
import com.hacof.communication.service.BoardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Optional<Board> boardOptional = boardRepository.findById(boardListRequestDTO.getBoardId());
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found!");
        }

        BoardList boardList = boardListMapper.toEntity(boardListRequestDTO, boardOptional.get());
        boardList = boardListRepository.save(boardList);

        return boardListMapper.toDto(boardList);
    }
}
