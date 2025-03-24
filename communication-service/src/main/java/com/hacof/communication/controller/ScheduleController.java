package com.hacof.communication.controller;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> createSchedule(
            @RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        CommonResponse<ScheduleResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleResponseDTO createdSchedule = scheduleService.createSchedule(scheduleRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Schedule created successfully!");
            response.setData(createdSchedule);
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
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> updateSchedule(
            @PathVariable Long id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) {
        CommonResponse<ScheduleResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleResponseDTO updatedSchedule = scheduleService.updateSchedule(id, scheduleRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule updated successfully!");
            response.setData(updatedSchedule);
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
}
