package com.hacof.communication.controller;

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ScheduleEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule-events")
public class ScheduleEventController {

    @Autowired
    private ScheduleEventService scheduleEventService;

    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleEventResponseDTO>> createScheduleEvent(
            @RequestBody ScheduleEventRequestDTO scheduleEventRequestDTO) {
        CommonResponse<ScheduleEventResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventResponseDTO createdScheduleEvent = scheduleEventService.createScheduleEvent(scheduleEventRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Schedule Event created successfully!");
            response.setData(createdScheduleEvent);
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
    public ResponseEntity<CommonResponse<ScheduleEventResponseDTO>> updateScheduleEvent(
            @PathVariable Long id, @RequestBody ScheduleEventRequestDTO scheduleEventRequestDTO) {
        CommonResponse<ScheduleEventResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventResponseDTO updatedScheduleEvent = scheduleEventService.updateScheduleEvent(id, scheduleEventRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event updated successfully!");
            response.setData(updatedScheduleEvent);
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
    public ResponseEntity<CommonResponse<String>> deleteScheduleEvent(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            scheduleEventService.deleteScheduleEvent(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Schedule Event deleted successfully!");
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
}
