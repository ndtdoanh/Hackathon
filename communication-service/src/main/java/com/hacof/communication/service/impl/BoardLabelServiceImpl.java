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
        // Validate Board existence
        Optional<Board> boardOptional = boardRepository.findById(Long.valueOf(boardLabelRequestDTO.getBoardId()));
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board with ID " + boardLabelRequestDTO.getBoardId() + " not found!");
        }

        // Validate BoardLabel name
        if (boardLabelRequestDTO.getName() == null
                || boardLabelRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Board label name cannot be empty.");
        }

        // Check for duplicate board label in the same board
        Optional<BoardLabel> existingBoardLabel = boardLabelRepository.findByNameAndBoardId(
                boardLabelRequestDTO.getName(), Long.valueOf(boardLabelRequestDTO.getBoardId()));
        if (existingBoardLabel.isPresent()) {
            throw new IllegalArgumentException("A board label with the same name already exists in this board.");
        }

        // Validate color (optional check based on your requirements)
        if (boardLabelRequestDTO.getColor() == null
                || boardLabelRequestDTO.getColor().isEmpty()) {
            throw new IllegalArgumentException("Board label color cannot be empty.");
        }

        // Create the new BoardLabel
        BoardLabel boardLabel = boardLabelMapper.toEntity(boardLabelRequestDTO, boardOptional.get());
        boardLabel = boardLabelRepository.save(boardLabel);

        return boardLabelMapper.toDto(boardLabel);
    }

    @Override
    public BoardLabelResponseDTO updateBoardLabel(Long id, BoardLabelRequestDTO boardLabelRequestDTO) {
        // Validate BoardLabel existence
        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(id);
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("Board label with ID " + id + " not found!");
        }

        // Validate Board existence for the update
        Optional<Board> boardOptional = boardRepository.findById(Long.valueOf(boardLabelRequestDTO.getBoardId()));
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board with ID " + boardLabelRequestDTO.getBoardId() + " not found!");
        }

        // Check for duplicate board label name in the same board
        Optional<BoardLabel> existingBoardLabel = boardLabelRepository.findByNameAndBoardId(
                boardLabelRequestDTO.getName(), Long.valueOf(boardLabelRequestDTO.getBoardId()));
        if (existingBoardLabel.isPresent()
                && !Long.valueOf(existingBoardLabel.get().getId()).equals(Long.valueOf(id))) {
            throw new IllegalArgumentException("A board label with the same name already exists in this board.");
        }

        // Validate name and color for the update
        if (boardLabelRequestDTO.getName() == null
                || boardLabelRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Board label name cannot be empty.");
        }

        if (boardLabelRequestDTO.getColor() == null
                || boardLabelRequestDTO.getColor().isEmpty()) {
            throw new IllegalArgumentException("Board label color cannot be empty.");
        }

        // Update the existing BoardLabel
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
            throw new IllegalArgumentException("Board label with ID " + id + " not found!");
        }
        boardLabelRepository.deleteById(id);
    }

    @Override
    public BoardLabelResponseDTO getBoardLabel(Long id) {
        Optional<BoardLabel> boardLabelOptional = boardLabelRepository.findById(id);
        if (!boardLabelOptional.isPresent()) {
            throw new IllegalArgumentException("Board label with ID " + id + " not found!");
        }
        return boardLabelMapper.toDto(boardLabelOptional.get());
    }

    @Override
    public List<BoardLabelResponseDTO> getAllBoardLabels() {
        List<BoardLabel> boardLabels = boardLabelRepository.findAll();
        return boardLabels.stream().map(boardLabelMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BoardLabelResponseDTO> getBoardLabelsByBoardId(Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board with ID " + boardId + " not found!");
        }

        List<BoardLabel> boardLabels = boardLabelRepository.findByBoardId(boardId);
        return boardLabels.stream().map(boardLabelMapper::toDto).collect(Collectors.toList());
    }
}
