package com.hacof.communication.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.request.BulkBoardListUpdateRequestDTO;
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
        // Validate Board ID
        if (boardListRequestDTO.getBoardId() == null
                || boardListRequestDTO.getBoardId().isEmpty()) {
            throw new IllegalArgumentException("Board ID cannot be null or empty.");
        }

        Optional<Board> boardOptional = boardRepository.findById(Long.parseLong(boardListRequestDTO.getBoardId()));
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board with ID " + boardListRequestDTO.getBoardId() + " not found!");
        }

        // Validate Board List name and description
        if (boardListRequestDTO.getName() == null
                || boardListRequestDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Board List name cannot be null or empty.");
        }

        // Check for duplicate Board List name under the same Board
        Optional<BoardList> existingBoardList = boardListRepository.findByNameAndBoardId(
                boardListRequestDTO.getName(), Long.parseLong(boardListRequestDTO.getBoardId()));
        if (existingBoardList.isPresent()) {
            throw new IllegalArgumentException("Board List with the same name already exists in this Board.");
        }

        // Create BoardList from DTO and save
        BoardList boardList = boardListMapper.toEntity(boardListRequestDTO, boardOptional.get());
        boardList = boardListRepository.save(boardList);

        return boardListMapper.toDto(boardList);
    }

    @Override
    public BoardListResponseDTO updateBoardList(Long id, BoardListRequestDTO boardListRequestDTO) {
        // Validate Board List existence
        Optional<BoardList> boardListOptional = boardListRepository.findById(id);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList with ID " + id + " not found!");
        }

        // Validate Board existence
        Optional<Board> boardOptional = boardRepository.findById(Long.parseLong(boardListRequestDTO.getBoardId()));
        if (!boardOptional.isPresent()) {
            throw new IllegalArgumentException("Board with ID " + boardListRequestDTO.getBoardId() + " not found!");
        }

        // Validate Board List name and description
        if (boardListRequestDTO.getName() == null
                || boardListRequestDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Board List name cannot be null or empty.");
        }

        // Check for duplicate Board List name under the same Board, except the current one
        Optional<BoardList> existingBoardList = boardListRepository.findByNameAndBoardId(
                boardListRequestDTO.getName(), Long.parseLong(boardListRequestDTO.getBoardId()));
        if (existingBoardList.isPresent()
                && !Long.valueOf(existingBoardList.get().getId()).equals(Long.valueOf(id))) {
            throw new IllegalArgumentException("A Board List with the same name already exists in this Board.");
        }

        // Update the BoardList entity
        BoardList boardList = boardListOptional.get();
        boardList.setName(boardListRequestDTO.getName());
        boardList.setBoard(boardOptional.get());

        // Save updated BoardList
        boardList = boardListRepository.save(boardList);

        return boardListMapper.toDto(boardList);
    }

    @Override
    public void deleteBoardList(Long id) {
        // Validate Board List existence
        Optional<BoardList> boardListOptional = boardListRepository.findById(id);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList with ID " + id + " not found!");
        }

        // Delete the Board List
        boardListRepository.deleteById(id);
    }

    @Override
    public BoardListResponseDTO getBoardList(Long id) {
        // Validate Board List existence
        Optional<BoardList> boardListOptional = boardListRepository.findById(id);
        if (!boardListOptional.isPresent()) {
            throw new IllegalArgumentException("BoardList with ID " + id + " not found!");
        }

        return boardListMapper.toDto(boardListOptional.get());
    }

    @Override
    public List<BoardListResponseDTO> getAllBoardLists() {
        List<BoardList> boardLists = boardListRepository.findAll();
        return boardLists.stream().map(boardListMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BoardListResponseDTO> updateBulkBoardLists(List<BulkBoardListUpdateRequestDTO> bulkUpdateRequest) {
        List<BoardListResponseDTO> updatedBoardLists = new ArrayList<>();

        if (bulkUpdateRequest == null || bulkUpdateRequest.isEmpty()) {
            throw new IllegalArgumentException("Bulk update request cannot be empty.");
        }

        for (BulkBoardListUpdateRequestDTO updateRequest : bulkUpdateRequest) {
            if (updateRequest.getId() == null || updateRequest.getId().isEmpty()) {
                throw new IllegalArgumentException("BoardList ID cannot be empty.");
            }

            // Check if BoardList exists by ID
            Optional<BoardList> boardListOptional = boardListRepository.findById(Long.valueOf(updateRequest.getId()));
            if (!boardListOptional.isPresent()) {
                throw new IllegalArgumentException("BoardList with ID " + updateRequest.getId() + " not found!");
            }

            // Validate position
            if (updateRequest.getPosition() == null) {
                throw new IllegalArgumentException("Position must be greater than 0.");
            }

            // Update the position of the BoardList
            BoardList boardList = boardListOptional.get();
            boardList.setPosition(Integer.parseInt(updateRequest.getPosition()));

            // Save updated BoardList
            boardListRepository.save(boardList);

            // Convert updated BoardList to DTO and add to the list
            BoardListResponseDTO updatedBoardListDTO = boardListMapper.toDto(boardList);
            updatedBoardLists.add(updatedBoardListDTO);
        }

        return updatedBoardLists;
    }
}
