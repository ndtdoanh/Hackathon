package com.hacof.communication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.service.ScheduleEventService;
import com.hacof.communication.util.CommonRequest;
import com.hacof.communication.util.CommonResponse;

@RestController
@RequestMapping("/api/v1/schedule-events")
public class ScheduleEventController {

    @Autowired
    private ScheduleEventService scheduleEventService;

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
    public ResponseEntity<CommonResponse<ScheduleEventResponseDTO>> createScheduleEvent(
            @RequestBody CommonRequest<ScheduleEventRequestDTO> request) {
        CommonResponse<ScheduleEventResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventResponseDTO createdScheduleEvent = scheduleEventService.createScheduleEvent(request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Schedule Event created successfully!");
            response.setData(createdScheduleEvent);
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
    public ResponseEntity<CommonResponse<ScheduleEventResponseDTO>> updateScheduleEvent(
            @PathVariable Long id, @RequestBody CommonRequest<ScheduleEventRequestDTO> request) {
        CommonResponse<ScheduleEventResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventResponseDTO updatedScheduleEvent =
                    scheduleEventService.updateScheduleEvent(id, request.getData());
            setCommonResponseFields(response, request);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event updated successfully!");
            response.setData(updatedScheduleEvent);
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
    public ResponseEntity<CommonResponse<String>> deleteScheduleEvent(@PathVariable Long id) {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            scheduleEventService.deleteScheduleEvent(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setMessage("Schedule Event deleted successfully!");
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
    public ResponseEntity<CommonResponse<ScheduleEventResponseDTO>> getScheduleEvent(@PathVariable Long id) {
        CommonResponse<ScheduleEventResponseDTO> response = new CommonResponse<>();
        try {
            ScheduleEventResponseDTO scheduleEvent = scheduleEventService.getScheduleEvent(id);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Event fetched successfully!");
            response.setData(scheduleEvent);
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
    public ResponseEntity<CommonResponse<List<ScheduleEventResponseDTO>>> getAllScheduleEvents() {
        CommonResponse<List<ScheduleEventResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventResponseDTO> scheduleEvents = scheduleEventService.getAllScheduleEvents();
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule Events fetched successfully!");
            response.setData(scheduleEvents);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-schedule/{scheduleId}")
    public ResponseEntity<CommonResponse<List<ScheduleEventResponseDTO>>> getScheduleEventsByScheduleId(
            @PathVariable Long scheduleId) {
        CommonResponse<List<ScheduleEventResponseDTO>> response = new CommonResponse<>();
        try {
            List<ScheduleEventResponseDTO> scheduleEvents =
                    scheduleEventService.getScheduleEventsByScheduleId(scheduleId);
            setDefaultResponseFields(response);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Schedule events for the given scheduleId fetched successfully!");
            response.setData(scheduleEvents);
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

    @GetMapping("/{scheduleEventId}/file-urls")
    public ResponseEntity<CommonResponse<List<FileUrlResponse>>> getFileUrlsByScheduleEventId(
            @PathVariable Long scheduleEventId) {

        List<FileUrlResponse> fileUrls = scheduleEventService.getFileUrlsByScheduleEventId(scheduleEventId);

        CommonResponse<List<FileUrlResponse>> response = new CommonResponse<>();
        setDefaultResponseFields(response);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("File URLs fetched successfully!");
        response.setData(fileUrls);

        return ResponseEntity.ok(response);
    }
}
