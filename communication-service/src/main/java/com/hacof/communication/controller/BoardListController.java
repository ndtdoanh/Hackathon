package com.hacof.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.request.BulkBoardListUpdateRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.BoardListService;

@RestController
@RequestMapping("/api/v1/board-lists")
public class BoardListController {

    @Autowired
    private BoardListService boardListService;

    @PostMapping
    public ResponseEntity<CommonResponse<BoardListResponseDTO>> createBoardList(
            @RequestBody BoardListRequestDTO boardListRequestDTO) {
        CommonResponse<BoardListResponseDTO> response = new CommonResponse<>();
        try {
            BoardListResponseDTO createdBoardList = boardListService.createBoardList(boardListRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Board List created successfully!");
            response.setData(createdBoardList);
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
    public ResponseEntity<CommonResponse<BoardListResponseDTO>> updateBoardList(
            @PathVariable Long id, @RequestBody BoardListRequestDTO boardListRequestDTO) {
        CommonResponse<BoardListResponseDTO> response = new CommonResponse<>();
        try {
            BoardListResponseDTO updatedBoardList = boardListService.updateBoardList(id, boardListRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board List updated successfully!");
            response.setData(updatedBoardList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
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
    public ResponseEntity<CommonResponse<String>> deleteBoardList(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            boardListService.deleteBoardList(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Board List deleted successfully!");
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
    public ResponseEntity<CommonResponse<BoardListResponseDTO>> getBoardList(@PathVariable Long id) {
        CommonResponse<BoardListResponseDTO> response = new CommonResponse<>();
        try {
            BoardListResponseDTO boardList = boardListService.getBoardList(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board List fetched successfully!");
            response.setData(boardList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
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
    public ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> getAllBoardLists() {
        CommonResponse<List<BoardListResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardListResponseDTO> boardLists = boardListService.getAllBoardLists();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board Lists fetched successfully!");
            response.setData(boardLists);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/bulk-update")
    public ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> updateBulkBoardLists(
            @RequestBody List<BulkBoardListUpdateRequestDTO> bulkUpdateRequest) {
        CommonResponse<List<BoardListResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardListResponseDTO> updatedBoardLists = boardListService.updateBulkBoardLists(bulkUpdateRequest);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board Lists updated successfully!");
            response.setData(updatedBoardLists);

            return ResponseEntity.status(HttpStatus.OK).body(response);
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
}
