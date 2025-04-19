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

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;
import com.hacof.communication.service.ScheduleEventReminderService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/schedule-event-reminders")
public class ScheduleEventReminderController {

    @Autowired
    private ScheduleEventReminderService scheduleEventReminderService;

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
    public ResponseEntity<CommonResponse<ScheduleEventReminderResponseDTO>> createScheduleEventReminder(
            @RequestBody CommonRequest<ScheduleEventReminderRequestDTO> request) {
        CommonResponse<ScheduleEventReminderResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventReminderResponseDTO createdScheduleEventReminder =
                    scheduleEventReminderService.createScheduleEventReminder(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Schedule Event Reminder created successfully!");
            response.setData(createdScheduleEventReminder);
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
    public ResponseEntity<CommonResponse<ScheduleEventReminderResponseDTO>> updateScheduleEventReminder(
            @PathVariable Long id, @RequestBody CommonRequest<ScheduleEventReminderRequestDTO> request) {
        CommonResponse<ScheduleEventReminderResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventReminderResponseDTO updatedScheduleEventReminder =
                    scheduleEventReminderService.updateScheduleEventReminder(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Reminder updated successfully!");
            response.setData(updatedScheduleEventReminder);
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
    public ResponseEntity<CommonResponse<String>> deleteScheduleEventReminder(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            scheduleEventReminderService.deleteScheduleEventReminder(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Reminder deleted successfully!");
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
    public ResponseEntity<CommonResponse<List<ScheduleEventReminderResponseDTO>>> getScheduleEventReminder(@PathVariable Long id) {
        CommonResponse<List<ScheduleEventReminderResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventReminderResponseDTO> scheduleEventReminders = scheduleEventReminderService.getScheduleEventReminder(id);

            setDefaultResponseFields(response);

            if (scheduleEventReminders.isEmpty()) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                response.setMessage("ScheduleEventReminder not found!");
                response.setData(scheduleEventReminders);
            } else {
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Schedule Event Reminder fetched successfully!");
                response.setData(scheduleEventReminders);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ScheduleEventReminderResponseDTO>>> getAllScheduleEventReminders() {
        CommonResponse<List<ScheduleEventReminderResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventReminderResponseDTO> scheduleEventReminders =
                    scheduleEventReminderService.getAllScheduleEventReminders();
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Reminders fetched successfully!");
            response.setData(scheduleEventReminders);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-schedule-event/{scheduleEventId}")
    public ResponseEntity<CommonResponse<List<ScheduleEventReminderResponseDTO>>>
            getScheduleEventRemindersByScheduleEventId(@PathVariable Long scheduleEventId) {
        CommonResponse<List<ScheduleEventReminderResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventReminderResponseDTO> reminders =
                    scheduleEventReminderService.getScheduleEventRemindersByScheduleEventId(scheduleEventId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule event reminders fetched successfully!");
            response.setData(reminders);
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

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<CommonResponse<List<ScheduleEventReminderResponseDTO>>> getScheduleEventRemindersByUserId(
            @PathVariable Long userId) {
        CommonResponse<List<ScheduleEventReminderResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventReminderResponseDTO> reminders =
                    scheduleEventReminderService.getScheduleEventRemindersByUserId(userId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule event reminders for the given user fetched successfully!");
            response.setData(reminders);
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
}
