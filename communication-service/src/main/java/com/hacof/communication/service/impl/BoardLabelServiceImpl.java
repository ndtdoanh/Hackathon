package com.hacof.communication.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;
import com.hacof.communication.entity.Board;
import com.hacof.communication.entity.BoardLabel;
import com.hacof.communication.mapper.BoardLabelMapper;
import com.hacof.communication.repository.BoardLabelRepository;
import com.hacof.communication.repository.BoardRepository;
import com.hacof.communication.service.BoardLabelService;

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

    @Override
    public BoardLabelResponseDTO updateBoardLabel(Long id, BoardLabelRequestDTO boardLabelRequestDTO) {
        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(id);
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("BoardLabel not found!");
        }

        Optional<Board> boardOptional = boardRepository.findById(boardLabelRequestDTO.getBoardId());
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board not found!");
        }

        BoardLabel boardLabel = boardLabelOptional.get();
        boardLabel.setName(boardLabelRequestDTO.getName());
        boardLabel.setColor(boardLabelRequestDTO.getColor());
        boardLabel.setBoard(boardOptional.get());
        boardLabel = boardLabelRepository.save(boardLabel);

        return boardLabelMapper.toDto(boardLabel);
    }

    @Override
    public void deleteBoardLabel(Long id) {
        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(id);
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("BoardLabel not found!");
        }
        boardLabelRepository.deleteById(id);
    }

    @Override
    public BoardLabelResponseDTO getBoardLabel(Long id) {
        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(id);
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("BoardLabel not found!");
        }
        return boardLabelMapper.toDto(boardLabelOptional.get());
    }

    @Override
    public List<BoardLabelResponseDTO> getAllBoardLabels() {
        List<BoardLabel> boardLabels = boardLabelRepository.findAll();
        return boardLabels.stream().map(boardLabelMapper::toDto).collect(Collectors.toList());
    }
}
