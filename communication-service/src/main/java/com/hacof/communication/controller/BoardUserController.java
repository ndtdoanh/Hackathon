package com.hacof.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.BoardUserRequestDTO;
import com.hacof.communication.dto.response.BoardUserResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.BoardUserService;

@RestController
@RequestMapping("/api/v1/board-users")
public class BoardUserController {

    @Autowired
    private BoardUserService boardUserService;

    @PostMapping
    public ResponseEntity<CommonResponse<BoardUserResponseDTO>> createBoardUser(
            @RequestBody BoardUserRequestDTO boardUserRequestDTO) {
        CommonResponse<BoardUserResponseDTO> response = new CommonResponse<>();
        try {
            BoardUserResponseDTO createdBoardUser = boardUserService.createBoardUser(boardUserRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("BoardUser created successfully!");
            response.setData(createdBoardUser);
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
    public ResponseEntity<CommonResponse<BoardUserResponseDTO>> updateBoardUser(
            @PathVariable Long id, @RequestBody BoardUserRequestDTO boardUserRequestDTO) {
        CommonResponse<BoardUserResponseDTO> response = new CommonResponse<>();
        try {
            BoardUserResponseDTO updatedBoardUser = boardUserService.updateBoardUser(id, boardUserRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUser updated successfully!");
            response.setData(updatedBoardUser);
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
    public ResponseEntity<CommonResponse<String>> deleteBoardUser(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            boardUserService.deleteBoardUser(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("BoardUser deleted successfully!");
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
    public ResponseEntity<CommonResponse<BoardUserResponseDTO>> getBoardUser(@PathVariable Long id) {
        CommonResponse<BoardUserResponseDTO> response = new CommonResponse<>();
        try {
            BoardUserResponseDTO boardUser = boardUserService.getBoardUser(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUser fetched successfully!");
            response.setData(boardUser);
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
    public ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> getAllBoardUsers() {
        CommonResponse<List<BoardUserResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardUserResponseDTO> boardUsers = boardUserService.getAllBoardUsers();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUsers fetched successfully!");
            response.setData(boardUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-board/{boardId}")
    public ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> getBoardUsersByBoardId(
            @PathVariable Long boardId) {
        CommonResponse<List<BoardUserResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardUserResponseDTO> boardUsers = boardUserService.getBoardUsersByBoardId(boardId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUsers for board fetched successfully!");
            response.setData(boardUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> getBoardUsersByUserId(@PathVariable Long userId) {
        CommonResponse<List<BoardUserResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardUserResponseDTO> boardUsers = boardUserService.getBoardUsersByUserId(userId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUsers for user fetched successfully!");
            response.setData(boardUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
