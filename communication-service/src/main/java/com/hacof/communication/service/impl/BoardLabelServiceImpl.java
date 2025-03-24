package com.hacof.communication.service.impl;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardLabel;
import com.hacof.communication.mapper.BoardLabelMapper;
import com.hacof.communication.repository.BoardLabelRepository;
import com.hacof.communication.repository.BoardRepository;
import com.hacof.communication.service.BoardLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardLabelServiceImpl implements BoardLabelService {

    @Autowired
    private BoardLabelRepository boardLabelRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardLabelMapper boardLabelMapper;

    @Override
    public BoardLabelResponseDTO createBoardLabel(BoardLabelRequestDTO boardLabelRequestDTO) {
        Optional<Board> boardOptional = boardRepository.findById(boardLabelRequestDTO.getBoardId());
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found!");
        }

        BoardLabel boardLabel = boardLabelMapper.toEntity(boardLabelRequestDTO, boardOptional.get());
        boardLabel = boardLabelRepository.save(boardLabel);

        return boardLabelMapper.toDto(boardLabel);
    }
}
