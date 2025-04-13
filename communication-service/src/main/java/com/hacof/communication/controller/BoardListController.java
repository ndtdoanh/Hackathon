package com.hacof.communication.controller;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.BoardListRequestDTO;
import com.hacof.communication.dto.request.BulkBoardListUpdateRequestDTO;
import com.hacof.communication.dto.response.BoardListResponseDTO;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;
import com.hacof.communication.service.BoardListService;

@RestController
@RequestMapping("/api/v1/board-lists")
public class BoardListController {

    @Autowired
    private BoardListService boardListService;

    private void setCommonResponseFields(CommonResponse<?> response, CommonRequest<?> request) {
        response.setRequestId(request.getRequestId() != null ? request.getRequestId() : UUID.randomUUID().toString());
        response.setRequestDateTime(request.getRequestDateTime() != null ? request.getRequestDateTime() : LocalDateTime.now());
        response.setChannel(request.getChannel() != null ? request.getChannel() : "HACOF");
    }

    private void setDefaultResponseFields(CommonResponse<?> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    @PostMapping
    public ResponseEntity<CommonResponse<BoardListResponseDTO>> createBoardList(
            @RequestBody CommonRequest<BoardListRequestDTO> request) {
        CommonResponse<BoardListResponseDTO> response = new CommonResponse<>();
        try {
            BoardListResponseDTO createdBoardList = boardListService.createBoardList(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Board List created successfully!");
            response.setData(createdBoardList);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<BoardListResponseDTO>> updateBoardList(
            @PathVariable Long id, @RequestBody CommonRequest<BoardListRequestDTO> request) {
        CommonResponse<BoardListResponseDTO> response = new CommonResponse<>();
        try {
            BoardListResponseDTO updatedBoardList = boardListService.updateBoardList(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board List updated successfully!");
            response.setData(updatedBoardList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Board List deleted successfully!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board List fetched successfully!");
            response.setData(boardList);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board Lists fetched successfully!");
            response.setData(boardLists);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/bulk-update")
    public ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> updateBulkBoardLists(
            @RequestBody CommonRequest<List<BulkBoardListUpdateRequestDTO>> request) {
        CommonResponse<List<BoardListResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardListResponseDTO> updatedBoardLists = boardListService.updateBulkBoardLists(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board Lists updated successfully!");
            response.setData(updatedBoardLists);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-board/{boardId}")
    public ResponseEntity<CommonResponse<List<BoardListResponseDTO>>> getBoardListByBoardId(@PathVariable Long boardId) {
        CommonResponse<List<BoardListResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardListResponseDTO> boardLists = boardListService.getBoardListByBoardId(boardId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Board lists fetched successfully!");
            response.setData(boardLists);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
