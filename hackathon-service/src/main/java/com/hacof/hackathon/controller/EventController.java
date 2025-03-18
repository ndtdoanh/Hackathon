package com.hacof.hackathon.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hacof.hackathon.constant.StatusCode;
import com.hacof.hackathon.dto.EventDTO;
import com.hacof.hackathon.dto.EventRegistrationDTO;
import com.hacof.hackathon.entity.Event;
import com.hacof.hackathon.service.EventRegistrationService;
import com.hacof.hackathon.service.EventService;
import com.hacof.hackathon.specification.EventSpecification;
import com.hacof.hackathon.util.CommonRequest;
import com.hacof.hackathon.util.CommonResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {
    final EventService eventService;
    final EventRegistrationService eventRegistrationService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<EventDTO>>> getByAllCriteria(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime eventDate,
            @RequestParam(required = false) Boolean notificationSent,
            @RequestParam(required = false) String eventType,
            @RequestParam(required = false) Long hackathonId,
            @RequestParam(required = false) Long organizerId) {

        Specification<Event> spec = Specification.where(EventSpecification.hasId(id))
                .and(EventSpecification.hasName(name))
                .and(EventSpecification.hasDescription(description))
                .and(EventSpecification.hasEventDate(eventDate))
                .and(EventSpecification.hasNotificationSent(notificationSent))
                .and(EventSpecification.hasEventType(eventType))
                .and(EventSpecification.hasHackathonId(hackathonId))
                .and(EventSpecification.hasOrganizerId(organizerId));

        List<EventDTO> events = eventService.searchEvents(spec);
        CommonResponse<List<EventDTO>> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched events successfully"),
                events);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<EventDTO>> createEvent(@RequestBody @Valid CommonRequest<EventDTO> request) {
        log.debug("Received request to create event: {}", request);
        EventDTO createdEvent = eventService.createEvent(request.getData());
        CommonResponse<EventDTO> response = new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Event created successfully"),
                createdEvent);
        log.debug("Created event: {}", createdEvent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<CommonResponse<EventDTO>> updateEvent(@RequestBody @Valid EventDTO eventDTO) {
        log.debug("Received request to update event: {}", eventDTO);
        long id = eventDTO.getId();
        EventDTO updatedEvent = eventService.updateEvent(id, eventDTO);
        CommonResponse<EventDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Event updated successfully"),
                updatedEvent);
        log.debug("Updated event: {}", updatedEvent);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<CommonResponse<EventDTO>> deleteEvent(@RequestBody EventDTO eventDTO) {
        long id = eventDTO.getId();
        log.debug("Received request to delete event with id: {}", id);
        eventService.deleteEvent(id);
        CommonResponse<EventDTO> response = new CommonResponse<>(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                "HACOF",
                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Event deleted successfully"),
                null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<EventRegistrationDTO>> registerForEvent(
            @Valid @RequestBody CommonRequest<EventRegistrationDTO> request) {
        EventRegistrationDTO result = eventRegistrationService.registerForEvent(
                request.getData().getEventId(), request.getData().getUserId());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Event registration successful"),
                result));
    }

    @PostMapping("/approve")
    public ResponseEntity<CommonResponse<EventRegistrationDTO>> approveRegistration(
            @Valid @RequestBody CommonRequest<Long> request) {
        EventRegistrationDTO result =
                eventRegistrationService.approveRegistration(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Event registration approved"),
                result));
    }

    @PostMapping("/reject")
    public ResponseEntity<CommonResponse<EventRegistrationDTO>> rejectRegistration(
            @Valid @RequestBody CommonRequest<Long> request) {
        EventRegistrationDTO result = eventRegistrationService.rejectRegistration(request.getData(), request.getData());
        return ResponseEntity.ok(new CommonResponse<>(
                request.getRequestId(),
                LocalDateTime.now(),
                request.getChannel(),
                new CommonResponse.Result("0000", "Event registration rejected"),
                result));
    }
}
