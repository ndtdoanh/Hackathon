package com.hacof.communication.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hacof.communication.dto.request.ScheduleEventRequestDTO;
import com.hacof.communication.dto.response.FileUrlResponse;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.entity.FileUrl;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.mapper.FileUrlMapper;
import com.hacof.communication.mapper.ScheduleEventMapper;
import com.hacof.communication.repository.FileUrlRepository;
import com.hacof.communication.repository.ScheduleEventAttendeeRepository;
import com.hacof.communication.repository.ScheduleEventReminderRepository;
import com.hacof.communication.repository.ScheduleEventRepository;
import com.hacof.communication.repository.ScheduleRepository;
import com.hacof.communication.service.ScheduleEventService;

@Service
public class ScheduleEventServiceImpl implements ScheduleEventService {

    @Autowired
    private ScheduleEventRepository scheduleEventRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private FileUrlRepository fileUrlRepository;

    @Autowired
    private ScheduleEventReminderRepository scheduleEventReminderRepository;

    @Autowired
    private ScheduleEventAttendeeRepository scheduleEventAttendeeRepository;

    @Autowired
    private ScheduleEventMapper scheduleEventMapper;

    @Autowired
    private FileUrlMapper fileUrlMapper;

    @Override
    public ScheduleEventResponseDTO createScheduleEvent(ScheduleEventRequestDTO scheduleEventRequestDTO) {
        if (scheduleEventRequestDTO.getScheduleId() == null) {
            throw new IllegalArgumentException("ScheduleId must not be null");
        }

        // Retrieve the associated schedule
        Schedule schedule = scheduleRepository
                .findById(Long.parseLong(scheduleEventRequestDTO.getScheduleId()))
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found!"));

        // Check if an event with the same name already exists for this schedule
        boolean eventExists = scheduleEventRepository.existsByScheduleIdAndName(
                Long.parseLong(scheduleEventRequestDTO.getScheduleId()), scheduleEventRequestDTO.getName());
        if (eventExists) {
            throw new IllegalArgumentException("An event with the same name already exists for this schedule.");
        }

        // Validate event name and time range
        if (scheduleEventRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }

        if (scheduleEventRequestDTO.getStartTime().isAfter(scheduleEventRequestDTO.getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }

        // Handle file URLs
        List<FileUrl> fileUrls = null;
        if (scheduleEventRequestDTO.getFileUrls() != null
                && !scheduleEventRequestDTO.getFileUrls().isEmpty()) {
            // Find the file URLs that are provided and not yet associated with any ScheduleEvent
            fileUrls =
                    fileUrlRepository.findAllByFileUrlInAndScheduleEventIsNull(scheduleEventRequestDTO.getFileUrls());

            // Associate the files with the ScheduleEvent (this will be done once the entity is created)
        }

        // Convert the DTO to the entity, including the schedule and file URLs
        ScheduleEvent scheduleEvent = scheduleEventMapper.toEntity(scheduleEventRequestDTO, schedule, fileUrls);

        // Save the ScheduleEvent entity
        scheduleEvent = scheduleEventRepository.save(scheduleEvent);

        // Now link the file URLs to the ScheduleEvent and save them
        if (fileUrls != null && !fileUrls.isEmpty()) {
            for (FileUrl fileUrl : fileUrls) {
                fileUrl.setScheduleEvent(scheduleEvent); // Associate the file with the newly created schedule event
            }
            // Save the updated file URLs with the associated ScheduleEvent
            fileUrlRepository.saveAll(fileUrls);
        }

        // Reload the ScheduleEvent to ensure file URLs are correctly linked and return the response DTO
        scheduleEvent = scheduleEventRepository
                .findById(scheduleEvent.getId())
                .orElseThrow(() -> new IllegalArgumentException("Failed to reload ScheduleEvent"));

        return scheduleEventMapper.toDto(scheduleEvent); // Return the response DTO
    }

    @Override
    public ScheduleEventResponseDTO updateScheduleEventWithoutFiles(
            Long id, ScheduleEventRequestDTO scheduleEventRequestDTO) {
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(id);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        if (scheduleEventRequestDTO.getScheduleId() == null) {
            throw new IllegalArgumentException("ScheduleId must not be null");
        }

        Schedule schedule = scheduleRepository
                .findById(Long.parseLong(scheduleEventRequestDTO.getScheduleId()))
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found!"));

        boolean eventExists = scheduleEventRepository.existsByScheduleIdAndNameAndIdNot(
                Long.parseLong(scheduleEventRequestDTO.getScheduleId()), scheduleEventRequestDTO.getName(), id);
        if (eventExists) {
            throw new IllegalArgumentException("An event with the same name already exists for this schedule.");
        }

        if (scheduleEventRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }

        if (scheduleEventRequestDTO.getStartTime().isAfter(scheduleEventRequestDTO.getEndTime())) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }

        // Cập nhật scheduleEvent
        ScheduleEvent scheduleEvent = scheduleEventOptional.get();
        scheduleEvent.setSchedule(schedule);
        scheduleEvent.setName(scheduleEventRequestDTO.getName());
        scheduleEvent.setDescription(scheduleEventRequestDTO.getDescription());
        scheduleEvent.setLocation(scheduleEventRequestDTO.getLocation());
        scheduleEvent.setStartTime(scheduleEventRequestDTO.getStartTime());
        scheduleEvent.setEndTime(scheduleEventRequestDTO.getEndTime());
        scheduleEvent.setRecurring(scheduleEventRequestDTO.isRecurring());
        scheduleEvent.setRecurrenceRule(scheduleEventRequestDTO.getRecurrenceRule());
        scheduleEvent.setEventLabel(scheduleEventRequestDTO.getEventLabel());
        scheduleEvent = scheduleEventRepository.save(scheduleEvent);

        return scheduleEventMapper.toDto(scheduleEvent);
    }

    @Override
    public ScheduleEventResponseDTO updateScheduleEventFiles(Long id, ScheduleEventRequestDTO scheduleEventRequestDTO) {
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(id);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent with ID " + id + " not found!");
        }

        ScheduleEvent scheduleEvent = scheduleEventOptional.get();

        // If fileUrls are provided, process them
        if (scheduleEventRequestDTO.getFileUrls() != null
                && !scheduleEventRequestDTO.getFileUrls().isEmpty()) {
            // Fetch the fileUrls that exist in the DB and are not already linked to another scheduleEvent
            List<FileUrl> fileUrls =
                    fileUrlRepository.findAllByFileUrlInAndScheduleEventIsNull(scheduleEventRequestDTO.getFileUrls());

            // Validate that all the file URLs provided are valid
            if (fileUrls.size() != scheduleEventRequestDTO.getFileUrls().size()) {
                throw new IllegalArgumentException(
                        "Some file URLs are invalid or already associated with other schedule events.");
            }

            // Optionally remove old fileUrls if they should be replaced, or merge
            // For example, remove old fileUrls or clear and add only new ones
            // scheduleEvent.getFileUrls().clear();  // If you want to completely replace the fileUrls

            // Add new fileUrls to the scheduleEvent
            for (FileUrl file : fileUrls) {
                if (!scheduleEvent.getFileUrls().contains(file)) {
                    scheduleEvent.getFileUrls().add(file); // Add only new fileUrls
                    file.setScheduleEvent(scheduleEvent);
                }
            }

            // Save the updated file URLs
            fileUrlRepository.saveAll(fileUrls);

            // Save the updated scheduleEvent with the new fileUrls
            scheduleEvent = scheduleEventRepository.save(scheduleEvent);

            return scheduleEventMapper.toDto(scheduleEvent);
        }

        throw new IllegalArgumentException("No file URLs provided for update");
    }

    @Transactional
    @Override
    public void deleteScheduleEvent(Long id) {
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(id);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }

        ScheduleEvent scheduleEvent = scheduleEventOptional.get();

        scheduleEventAttendeeRepository.deleteByScheduleEvent(scheduleEvent);
        scheduleEventReminderRepository.deleteByScheduleEvent(scheduleEvent);

        scheduleEventRepository.deleteById(id);
    }

    @Override
    public ScheduleEventResponseDTO getScheduleEvent(Long id) {
        Optional<ScheduleEvent> scheduleEventOptional = scheduleEventRepository.findById(id);
        if (!scheduleEventOptional.isPresent()) {
            throw new IllegalArgumentException("ScheduleEvent not found!");
        }
        return scheduleEventMapper.toDto(scheduleEventOptional.get());
    }

    @Override
    public List<ScheduleEventResponseDTO> getAllScheduleEvents() {
        List<ScheduleEvent> scheduleEvents = scheduleEventRepository.findAll();
        return scheduleEvents.stream().map(scheduleEventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleEventResponseDTO> getScheduleEventsByScheduleId(Long scheduleId) {
        List<ScheduleEvent> scheduleEvents = scheduleEventRepository.findByScheduleId(scheduleId);
        if (scheduleEvents.isEmpty()) {
            return Collections.emptyList();
        }
        return scheduleEvents.stream().map(scheduleEventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<FileUrlResponse> getFileUrlsByScheduleEventId(Long scheduleEventId) {
        ScheduleEvent scheduleEvent = scheduleEventRepository
                .findById(scheduleEventId)
                .orElseThrow(() -> new IllegalArgumentException("ScheduleEvent not found!"));
        return fileUrlMapper.toResponseList(scheduleEvent.getFileUrls());
    }
}
