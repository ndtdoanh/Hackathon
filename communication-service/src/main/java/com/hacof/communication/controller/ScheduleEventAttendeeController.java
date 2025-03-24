package com.hacof.communication.controller;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventAttendeeResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ScheduleEventAttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule-event-attendees")
public class ScheduleEventAttendeeController {

    @Autowired
    private ScheduleEventAttendeeService scheduleEventAttendeeService;

    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleEventAttendeeResponseDTO>> createScheduleEventAttendee(
            @RequestBody ScheduleEventAttendeeRequestDTO scheduleEventAttendeeRequestDTO) {
        CommonResponse<ScheduleEventAttendeeResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventAttendeeResponseDTO createdScheduleEventAttendee = scheduleEventAttendeeService.createScheduleEventAttendee(scheduleEventAttendeeRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Schedule Event Attendee created successfully!");
            response.setData(createdScheduleEventAttendee);
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
    public ResponseEntity<CommonResponse<ScheduleEventAttendeeResponseDTO>> updateScheduleEventAttendee(
            @PathVariable Long id, @RequestBody ScheduleEventAttendeeRequestDTO scheduleEventAttendeeRequestDTO) {
        CommonResponse<ScheduleEventAttendeeResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventAttendeeResponseDTO updatedScheduleEventAttendee = scheduleEventAttendeeService.updateScheduleEventAttendee(id, scheduleEventAttendeeRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Attendee updated successfully!");
            response.setData(updatedScheduleEventAttendee);
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
    public ResponseEntity<CommonResponse<String>> deleteScheduleEventAttendee(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            scheduleEventAttendeeService.deleteScheduleEventAttendee(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Schedule Event Attendee deleted successfully!");
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
    public ResponseEntity<CommonResponse<ScheduleEventAttendeeResponseDTO>> getScheduleEventAttendee(@PathVariable Long id) {
        CommonResponse<ScheduleEventAttendeeResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventAttendeeResponseDTO scheduleEventAttendee = scheduleEventAttendeeService.getScheduleEventAttendee(id);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Attendee fetched successfully!");
            response.setData(scheduleEventAttendee);
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
    public ResponseEntity<CommonResponse<List<ScheduleEventAttendeeResponseDTO>>> getAllScheduleEventAttendees() {
        CommonResponse<List<ScheduleEventAttendeeResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventAttendeeResponseDTO> scheduleEventAttendees = scheduleEventAttendeeService.getAllScheduleEventAttendees();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Attendees fetched successfully!");
            response.setData(scheduleEventAttendees);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CommonResponse<ScheduleEventAttendeeResponseDTO>> changeStatus(
            @PathVariable Long id, @RequestParam("status") ScheduleEventStatus status) {
        CommonResponse<ScheduleEventAttendeeResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventAttendeeResponseDTO updatedScheduleEventAttendee = scheduleEventAttendeeService.changeStatus(id, status);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Attendee status updated successfully!");
            response.setData(updatedScheduleEventAttendee);
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
