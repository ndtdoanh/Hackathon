package com.hacof.communication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ScheduleService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteSchedule(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            scheduleService.deleteSchedule(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Schedule deleted successfully!");
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
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> getSchedule(@PathVariable Long id) {
        CommonResponse<ScheduleResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleResponseDTO schedule = scheduleService.getSchedule(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule fetched successfully!");
            response.setData(schedule);
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
    public ResponseEntity<CommonResponse<List<ScheduleResponseDTO>>> getAllSchedules() {
        CommonResponse<List<ScheduleResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleResponseDTO> schedules = scheduleService.getAllSchedules();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedules fetched successfully!");
            response.setData(schedules);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-team/{teamId}")
    public ResponseEntity<CommonResponse<List<ScheduleResponseDTO>>> getSchedulesByTeamId(@PathVariable Long teamId) {
        CommonResponse<List<ScheduleResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleResponseDTO> schedules = scheduleService.getSchedulesByTeamId(teamId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedules for team fetched successfully!");
            response.setData(schedules);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-created-and-hackathon")
    public ResponseEntity<CommonResponse<List<ScheduleResponseDTO>>> getSchedulesByCreatedByUsernameAndHackathonId(
            @RequestParam String createdByUsername, @RequestParam Long hackathonId) {
        CommonResponse<List<ScheduleResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleResponseDTO> schedules =
                    scheduleService.getSchedulesByCreatedByUsernameAndHackathonId(createdByUsername, hackathonId);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedules filtered by createdByUsername and hackathonId fetched successfully!");
            response.setData(schedules);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
