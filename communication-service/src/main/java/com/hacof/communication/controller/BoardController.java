package com.hacof.communication.controller;

import com.hacof.communication.dto.request.BoardRequestDTO;
import com.hacof.communication.dto.response.BoardResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity<CommonResponse<BoardResponseDTO>> createBoard(
            @RequestBody BoardRequestDTO boardRequestDTO) {
        CommonResponse<BoardResponseDTO> response = new CommonResponse<>();
        try {
            BoardResponseDTO createdBoard = boardService.createBoard(boardRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Board created successfully!");
            response.setData(createdBoard);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<BoardResponseDTO>> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardRequestDTO boardRequestDTO) {

        CommonResponse<BoardResponseDTO> response = new CommonResponse<>();
        try {
            BoardResponseDTO updatedBoard = boardService.updateBoard(id, boardRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board updated successfully!");
            response.setData(updatedBoard);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
