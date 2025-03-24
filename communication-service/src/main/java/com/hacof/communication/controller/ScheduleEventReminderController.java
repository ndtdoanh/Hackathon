package com.hacof.communication.controller;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;
import com.hacof.communication.response.CommonResponse;
import com.hacof.communication.service.ScheduleEventReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule-event-reminders")
public class ScheduleEventReminderController {

    @Autowired
    private ScheduleEventReminderService scheduleEventReminderService;

    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleEventReminderResponseDTO>> createScheduleEventReminder(
            @RequestBody ScheduleEventReminderRequestDTO scheduleEventReminderRequestDTO) {
        CommonResponse<ScheduleEventReminderResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventReminderResponseDTO createdScheduleEventReminder = scheduleEventReminderService.createScheduleEventReminder(scheduleEventReminderRequestDTO);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Schedule Event Reminder created successfully!");
            response.setData(createdScheduleEventReminder);
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
    public ResponseEntity<CommonResponse<ScheduleEventReminderResponseDTO>> updateScheduleEventReminder(
            @PathVariable Long id, @RequestBody ScheduleEventReminderRequestDTO scheduleEventReminderRequestDTO) {
        CommonResponse<ScheduleEventReminderResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventReminderResponseDTO updatedScheduleEventReminder = scheduleEventReminderService.updateScheduleEventReminder(id, scheduleEventReminderRequestDTO);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Reminder updated successfully!");
            response.setData(updatedScheduleEventReminder);
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
    public ResponseEntity<CommonResponse<String>> deleteScheduleEventReminder(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            scheduleEventReminderService.deleteScheduleEventReminder(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Schedule Event Reminder deleted successfully!");
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
