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
}
