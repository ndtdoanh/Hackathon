package com.hacof.communication.mapper;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.*;
import com.hacof.communication.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScheduleEventReminderMapper {

    // Convert from ScheduleEventReminderRequestDTO to ScheduleEventReminder entity
    public ScheduleEventReminder toEntity(ScheduleEventReminderRequestDTO requestDTO, ScheduleEvent scheduleEvent, User user) {
        return ScheduleEventReminder.builder()
                .scheduleEvent(scheduleEvent)
                .user(user)
                .remindAt(requestDTO.getRemindAt())
                .build();
    }

    // Convert from ScheduleEventReminder entity to ScheduleEventReminderResponseDTO
    public ScheduleEventReminderResponseDTO toDto(ScheduleEventReminder scheduleEventReminder) {
        if (scheduleEventReminder == null) {
            return null;
        }

        ScheduleEvent scheduleEvent = scheduleEventReminder.getScheduleEvent();
        User user = scheduleEventReminder.getUser();

        return ScheduleEventReminderResponseDTO.builder()
                .id(scheduleEventReminder.getId())
                .scheduleEvent(scheduleEvent != null ? mapScheduleEventToDto(scheduleEvent) : null)
                .user(user != null ? mapUserToDto(user) : null)  // Map full UserResponse
                .remindAt(scheduleEventReminder.getRemindAt())
                .createdDate(scheduleEventReminder.getCreatedDate())
                .lastModifiedDate(scheduleEventReminder.getLastModifiedDate())
                .build();
    }

    // Convert ScheduleEvent to ScheduleEventResponseDTO
    private ScheduleEventResponseDTO mapScheduleEventToDto(ScheduleEvent scheduleEvent) {
        return ScheduleEventResponseDTO.builder()
                .id(scheduleEvent.getId())
                .schedule(scheduleEvent.getSchedule() != null ? mapScheduleToDto(scheduleEvent.getSchedule()) : null)
                .name(scheduleEvent.getName())
                .description(scheduleEvent.getDescription())
                .location(scheduleEvent.getLocation())
                .startTime(scheduleEvent.getStartTime())
                .endTime(scheduleEvent.getEndTime())
                .isRecurring(scheduleEvent.isRecurring())
                .recurrenceRule(scheduleEvent.getRecurrenceRule())
                .createdDate(scheduleEvent.getCreatedDate())
                .lastModifiedDate(scheduleEvent.getLastModifiedDate())
                .createdBy(scheduleEvent.getCreatedBy() != null ? scheduleEvent.getCreatedBy().getUsername() : null)
                .fileUrls(mapFileUrls(scheduleEvent))
                .build();
    }

    // Convert Schedule to ScheduleResponseDTO
    private ScheduleResponseDTO mapScheduleToDto(Schedule schedule) {
        return ScheduleResponseDTO.builder()
                .id(schedule.getId())
                .teamId(schedule.getTeam() != null ? schedule.getTeam().getId() : null)
                .name(schedule.getName())
                .description(schedule.getDescription())
                .createdDate(schedule.getCreatedDate())
                .lastModifiedDate(schedule.getLastModifiedDate())
                .createdBy(schedule.getCreatedBy() != null ? schedule.getCreatedBy().getUsername() : null)
                .build();
    }

    // Convert User entity to UserResponse DTO
    private UserResponse mapUserToDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isVerified(user.getIsVerified())
                .status(user.getStatus())
                .noPassword(user.getPassword() == null || user.getPassword().isEmpty()) // Derived field
                .createdDate(user.getCreatedDate())
                .lastModifiedDate(user.getLastModifiedDate())
                .createdByUserId(user.getCreatedBy() != null ? user.getCreatedBy().getId() : null)
                .roles(user.getUserRoles() != null ? mapUserRoles(user.getUserRoles()) : Collections.emptySet())
                .build();
    }

    // Convert Set<UserRole> to Set<RoleResponse>
    private Set<RoleResponse> mapUserRoles(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> RoleResponse.builder()
                        .id(userRole.getRole().getId())
                        .name(userRole.getRole().getName())
                        .description(userRole.getRole().getDescription())
                        .createdDate(userRole.getRole().getCreatedDate())
                        .lastModifiedDate(userRole.getRole().getLastModifiedDate())
                        .createdByUserId(userRole.getRole().getCreatedBy() != null ? userRole.getRole().getCreatedBy().getId() : null)
                        .permissions(userRole.getRole().getPermissions() != null ? mapPermissions(userRole.getRole().getPermissions()) : Collections.emptySet())
                        .build())
                .collect(Collectors.toSet());
    }

    // Convert Set<Permission> to Set<PermissionResponse>
    private Set<PermissionResponse> mapPermissions(Set<Permission> permissions) {
        return permissions.stream()
                .map(permission -> PermissionResponse.builder()
                        .id(permission.getId())
                        .name(permission.getName())
                        .apiPath(permission.getApiPath())
                        .method(permission.getMethod())
                        .module(permission.getModule())
                        .build())
                .collect(Collectors.toSet());
    }

    // Convert fileUrls from ScheduleEvent to List<String>
    private List<String> mapFileUrls(ScheduleEvent scheduleEvent) {
        return scheduleEvent.getFileUrls() != null
                ? scheduleEvent.getFileUrls().stream().map(file -> file.getFileUrl()).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
