package com.hacof.hackathon.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hacof.hackathon.dto.EventDTO;
import com.hacof.hackathon.dto.EventRegistrationDTO;
import com.hacof.hackathon.service.EventRegistrationService;
import com.hacof.hackathon.service.EventService;
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
    private final EventRegistrationService eventRegistrationService;

    // final EventRegistrationService eventRegistrationService;

    //    @GetMapping
    //    public ResponseEntity<CommonResponse<List<EventDTO>>> getByAllCriteria(
    //            @RequestParam(required = false) Long id,
    //            @RequestParam(required = false) String name,
    //            @RequestParam(required = false) String description,
    //            @RequestParam(required = false) LocalDateTime eventDate,
    //            @RequestParam(required = false) Boolean notificationSent,
    //            @RequestParam(required = false) String eventType,
    //            @RequestParam(required = false) Long hackathonId,
    //            @RequestParam(required = false) Long organizerId) {
    //
    //        Specification<Event> spec = Specification.where(EventSpecification.hasId(id))
    //                .and(EventSpecification.hasName(name))
    //                .and(EventSpecification.hasDescription(description))
    //                .and(EventSpecification.hasEventDate(eventDate))
    //                .and(EventSpecification.hasNotificationSent(notificationSent))
    //                .and(EventSpecification.hasEventType(eventType))
    //                .and(EventSpecification.hasHackathonId(hackathonId))
    //                .and(EventSpecification.hasOrganizerId(organizerId));
    //
    //        List<EventDTO> events = eventService.searchEvents(spec);
    //        CommonResponse<List<EventDTO>> response = new CommonResponse<>(
    //                UUID.randomUUID().toString(),
    //                LocalDateTime.now(),
    //                "HACOF",
    //                new CommonResponse.Result(StatusCode.SUCCESS.getCode(), "Fetched events successfully"),
    //                events);
    //        return ResponseEntity.ok(response);
    //    }

    @PostMapping
    public ResponseEntity<CommonResponse<EventDTO>> createEvent(@RequestBody @Valid CommonRequest<EventDTO> request) {
        EventDTO eventDTO = eventService.create(request.getData());
        CommonResponse<EventDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Event created successfully"), eventDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<CommonResponse<EventDTO>> updateEvent(@RequestBody @Valid CommonRequest<EventDTO> request) {
        EventDTO eventDTO = eventService.update(request.getData().getId(), request.getData());
        CommonResponse<EventDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Event updated successfully"), eventDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<CommonResponse<EventDTO>> deleteEvent(@RequestBody @Valid CommonRequest<EventDTO> request) {
        eventService.delete(request.getData().getId());
        CommonResponse<EventDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Event deleted successfully"), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<EventDTO>>> getAllEvents() {
        List<EventDTO> events = eventService.getAll();
        CommonResponse<List<EventDTO>> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched all events successfully"), events);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<EventDTO>> getEventById(@PathVariable Long id) {
        EventDTO event = eventService.getById(id);
        CommonResponse<EventDTO> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched event successfully"), event);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    public ResponseEntity<CommonResponse<EventRegistrationDTO>> createEventRegistration(
            @RequestBody @Valid CommonRequest<EventRegistrationDTO> request) {
        EventRegistrationDTO eventRegistrationDTO = eventRegistrationService.create(request.getData());
        CommonResponse<EventRegistrationDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Event registration created successfully"), eventRegistrationDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/registration")
    public ResponseEntity<CommonResponse<EventRegistrationDTO>> updateEventRegistration(
            @RequestBody @Valid CommonRequest<EventRegistrationDTO> request) {
        EventRegistrationDTO eventRegistrationDTO =
                eventRegistrationService.update(request.getData().getId(), request.getData());
        CommonResponse<EventRegistrationDTO> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Event registration updated successfully"), eventRegistrationDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/registration")
    public ResponseEntity<CommonResponse<Void>> deleteEventRegistration(
            @RequestBody @Valid CommonRequest<Long> request) {
        eventRegistrationService.delete(request.getData());
        CommonResponse<Void> response = new CommonResponse<>(
                //                request.getRequestId(),
                //                LocalDateTime.now(),
                //                request.getChannel(),
                new CommonResponse.Result("0000", "Event registration deleted successfully"), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/registration")
    public ResponseEntity<CommonResponse<List<EventRegistrationDTO>>> getAllEventRegistrations() {
        List<EventRegistrationDTO> eventRegistrations = eventRegistrationService.getAll();
        CommonResponse<List<EventRegistrationDTO>> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched all event registrations successfully"), eventRegistrations);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/registration/{id}")
    public ResponseEntity<CommonResponse<EventRegistrationDTO>> getEventRegistrationById(@PathVariable Long id) {
        EventRegistrationDTO eventRegistration = eventRegistrationService.getById(id);
        CommonResponse<EventRegistrationDTO> response = new CommonResponse<>(
                //                UUID.randomUUID().toString(),
                //                LocalDateTime.now(),
                //                "HACOF",
                new CommonResponse.Result("0000", "Fetched event registration successfully"), eventRegistration);
        return ResponseEntity.ok(response);
    }
}
