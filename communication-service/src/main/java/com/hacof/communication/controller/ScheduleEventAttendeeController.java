package com.hacof.communication.controller;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.ScheduleEventAttendeeResponseDTO;
import com.hacof.communication.service.ScheduleEventAttendeeService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schedule-event-attendees")
public class ScheduleEventAttendeeController {

    @Autowired
    private ScheduleEventAttendeeService scheduleEventAttendeeService;

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
    public ResponseEntity<CommonResponse<ScheduleEventAttendeeResponseDTO>> createScheduleEventAttendee(
            @RequestBody CommonRequest<ScheduleEventAttendeeRequestDTO> request) {
        CommonResponse<ScheduleEventAttendeeResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventAttendeeResponseDTO createdScheduleEventAttendee =
                    scheduleEventAttendeeService.createScheduleEventAttendee(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Schedule Event Attendee created successfully!");
            response.setData(createdScheduleEventAttendee);
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
    public ResponseEntity<CommonResponse<ScheduleEventAttendeeResponseDTO>> updateScheduleEventAttendee(
            @PathVariable Long id, @RequestBody CommonRequest<ScheduleEventAttendeeRequestDTO> request) {
        CommonResponse<ScheduleEventAttendeeResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventAttendeeResponseDTO updatedScheduleEventAttendee =
                    scheduleEventAttendeeService.updateScheduleEventAttendee(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Attendee updated successfully!");
            response.setData(updatedScheduleEventAttendee);
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
    public ResponseEntity<CommonResponse<String>> deleteScheduleEventAttendee(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            scheduleEventAttendeeService.deleteScheduleEventAttendee(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Schedule Event Attendee deleted successfully!");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
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
    public ResponseEntity<CommonResponse<ScheduleEventAttendeeResponseDTO>> getScheduleEventAttendee(
            @PathVariable Long id) {
        CommonResponse<ScheduleEventAttendeeResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventAttendeeResponseDTO scheduleEventAttendee =
                    scheduleEventAttendeeService.getScheduleEventAttendee(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Attendee fetched successfully!");
            response.setData(scheduleEventAttendee);
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
    public ResponseEntity<CommonResponse<List<ScheduleEventAttendeeResponseDTO>>> getAllScheduleEventAttendees() {
        CommonResponse<List<ScheduleEventAttendeeResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventAttendeeResponseDTO> scheduleEventAttendees =
                    scheduleEventAttendeeService.getAllScheduleEventAttendees();
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Attendees fetched successfully!");
            response.setData(scheduleEventAttendees);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
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
            ScheduleEventAttendeeResponseDTO updatedScheduleEventAttendee =
                    scheduleEventAttendeeService.changeStatus(id, status);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event Attendee status updated successfully!");
            response.setData(updatedScheduleEventAttendee);
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

    @GetMapping("/by-event/{scheduleEventId}")
    public ResponseEntity<CommonResponse<List<ScheduleEventAttendeeResponseDTO>>> getScheduleEventAttendeesByEventId(
            @PathVariable Long scheduleEventId) {
        CommonResponse<List<ScheduleEventAttendeeResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventAttendeeResponseDTO> attendees =
                    scheduleEventAttendeeService.getScheduleEventAttendeesByEventId(scheduleEventId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule event attendees fetched successfully!");
            response.setData(attendees);
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
