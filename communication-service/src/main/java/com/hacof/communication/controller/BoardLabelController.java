package com.hacof.communication.controller;

import com.hacof.communication.dto.request.BoardLabelRequestDTO;
import com.hacof.communication.dto.response.BoardLabelResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.BoardLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/board-labels")
public class BoardLabelController {

    @Autowired
    private BoardLabelService boardLabelService;

    @PostMapping
    public ResponseEntity<CommonResponse<BoardLabelResponseDTO>> createBoardLabel(
            @RequestBody BoardLabelRequestDTO boardLabelRequestDTO) {
        CommonResponse<BoardLabelResponseDTO> response = new CommonResponse<>();
        try {
            BoardLabelResponseDTO createdBoardLabel = boardLabelService.createBoardLabel(boardLabelRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Board Label created successfully!");
            response.setData(createdBoardLabel);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<BoardLabelResponseDTO>> updateBoardLabel(
            @PathVariable Long id, @RequestBody BoardLabelRequestDTO boardLabelRequestDTO) {
        CommonResponse<BoardLabelResponseDTO> response = new CommonResponse<>();
        try {
            BoardLabelResponseDTO updatedBoardLabel = boardLabelService.updateBoardLabel(id, boardLabelRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board Label updated successfully!");
            response.setData(updatedBoardLabel);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteBoardLabel(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            boardLabelService.deleteBoardLabel(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Board Label deleted successfully!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<BoardLabelResponseDTO>> getBoardLabel(@PathVariable Long id) {
        CommonResponse<BoardLabelResponseDTO> response = new CommonResponse<>();
        try {
            BoardLabelResponseDTO boardLabel = boardLabelService.getBoardLabel(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board Label fetched successfully!");
            response.setData(boardLabel);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardLabelResponseDTO>>> getAllBoardLabels() {
        CommonResponse<List<BoardLabelResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardLabelResponseDTO> boardLabels = boardLabelService.getAllBoardLabels();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board Labels fetched successfully!");
            response.setData(boardLabels);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
