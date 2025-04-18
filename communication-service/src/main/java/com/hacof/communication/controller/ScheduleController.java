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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.communication.dto.request.ScheduleRequestDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.service.ScheduleService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

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
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> createSchedule(
            @RequestBody CommonRequest<ScheduleRequestDTO> request) {
        CommonResponse<ScheduleResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleResponseDTO createdSchedule = scheduleService.createSchedule(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Schedule created successfully!");
            response.setData(createdSchedule);
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
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> updateSchedule(
            @PathVariable Long id, @RequestBody CommonRequest<ScheduleRequestDTO> request) {
        CommonResponse<ScheduleResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleResponseDTO updatedSchedule = scheduleService.updateSchedule(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule updated successfully!");
            response.setData(updatedSchedule);
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
    public ResponseEntity<CommonResponse<String>> deleteSchedule(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            scheduleService.deleteSchedule(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule deleted successfully!");
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
    public ResponseEntity<CommonResponse<ScheduleResponseDTO>> getSchedule(@PathVariable Long id) {
        CommonResponse<ScheduleResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleResponseDTO schedule = scheduleService.getSchedule(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule fetched successfully!");
            response.setData(schedule);
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
    public ResponseEntity<CommonResponse<List<ScheduleResponseDTO>>> getAllSchedules() {
        CommonResponse<List<ScheduleResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleResponseDTO> schedules = scheduleService.getAllSchedules();
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedules fetched successfully!");
            response.setData(schedules);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedules for team fetched successfully!");
            response.setData(schedules);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedules filtered by createdByUsername and hackathonId fetched successfully!");
            response.setData(schedules);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-team-and-hackathon")
    public ResponseEntity<CommonResponse<List<ScheduleResponseDTO>>> getSchedulesByTeamIdAndHackathonId(
            @RequestParam Long teamId, @RequestParam Long hackathonId) {

        CommonResponse<List<ScheduleResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleResponseDTO> schedules =
                    scheduleService.getSchedulesByTeamIdAndHackathonId(teamId, hackathonId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedules fetched successfully for given team and hackathon.");
            response.setData(schedules);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
