package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.communication.dto.request.BoardUserRequestDTO;
import com.hacof.communication.dto.response.BoardUserResponseDTO;
import com.hacof.communication.service.BoardUserService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/board-users")
public class BoardUserController {

    @Autowired
    private BoardUserService boardUserService;

    private void setCommonResponseFields(CommonResponse<?> response, CommonRequest<?> request) {
        response.setRequestId(
                request.getRequestId() != null
                        ? request.getRequestId()
                        : UUID.randomUUID().toString());
        response.setRequestDateTime(
                request.getRequestDateTime() != null ? request.getRequestDateTime() : LocalDateTime.now());
        response.setChannel(request.getChannel() != null ? request.getChannel() : "HACOF");
    }

    private void setDefaultResponseFields(CommonResponse<?> response) {
        response.setRequestId(UUID.randomUUID().toString());
        response.setRequestDateTime(LocalDateTime.now());
        response.setChannel("HACOF");
    }

    @PostMapping
    public ResponseEntity<CommonResponse<BoardUserResponseDTO>> createBoardUser(
            @RequestBody CommonRequest<BoardUserRequestDTO> request) {
        CommonResponse<BoardUserResponseDTO> response = new CommonResponse<>();
        try {
            BoardUserResponseDTO createdBoardUser = boardUserService.createBoardUser(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("BoardUser created successfully!");
            response.setData(createdBoardUser);
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
    public ResponseEntity<CommonResponse<BoardUserResponseDTO>> updateBoardUser(
            @PathVariable Long id, @RequestBody CommonRequest<BoardUserRequestDTO> request) {
        CommonResponse<BoardUserResponseDTO> response = new CommonResponse<>();
        try {
            BoardUserResponseDTO updatedBoardUser = boardUserService.updateBoardUser(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUser updated successfully!");
            response.setData(updatedBoardUser);
            return ResponseEntity.ok(response);
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
    public ResponseEntity<CommonResponse<BoardUserResponseDTO>> deleteBoardUser(@PathVariable Long id) {
        CommonResponse<BoardUserResponseDTO> response = new CommonResponse<>();
        try {
            BoardUserResponseDTO dto = boardUserService.deleteBoardUser(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUser deleted successfully!");
            response.setData(dto);
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

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<BoardUserResponseDTO>> getBoardUser(@PathVariable Long id) {
        CommonResponse<BoardUserResponseDTO> response = new CommonResponse<>();
        try {
            BoardUserResponseDTO boardUser = boardUserService.getBoardUser(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUser fetched successfully!");
            response.setData(boardUser);
            return ResponseEntity.ok(response);
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
    public ResponseEntity<CommonResponse<List<BoardUserResponseDTO>>> getAllBoardUsers() {
        CommonResponse<List<BoardUserResponseDTO>> response = new CommonResponse<>();
        try {
            List<BoardUserResponseDTO> boardUsers = boardUserService.getAllBoardUsers();
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUsers fetched successfully!");
            response.setData(boardUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUsers for board fetched successfully!");
            response.setData(boardUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("BoardUsers for user fetched successfully!");
            response.setData(boardUsers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
