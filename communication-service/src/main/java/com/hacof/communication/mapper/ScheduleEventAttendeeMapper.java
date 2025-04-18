package com.hacof.communication.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.PermissionResponse;
import com.hacof.communication.dto.response.RoleResponse;
import com.hacof.communication.dto.response.ScheduleEventAttendeeResponseDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.dto.response.UserResponse;
import com.hacof.communication.entity.Permission;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.entity.ScheduleEventAttendee;
import com.hacof.communication.entity.User;
import com.hacof.communication.entity.UserRole;

@Component
public class ScheduleEventAttendeeMapper {

    public ScheduleEventAttendee toEntity(
            ScheduleEventAttendeeRequestDTO requestDTO, ScheduleEvent scheduleEvent, User user) {
        return ScheduleEventAttendee.builder()
                .scheduleEvent(scheduleEvent)
                .user(user)
                .status(ScheduleEventStatus.INVITED)
                .build();
    }

    public ScheduleEventAttendeeResponseDTO toDto(ScheduleEventAttendee scheduleEventAttendee) {
        if (scheduleEventAttendee == null) {
            return null;
        }

        ScheduleEvent scheduleEvent = scheduleEventAttendee.getScheduleEvent();
        User user = scheduleEventAttendee.getUser();

        return ScheduleEventAttendeeResponseDTO.builder()
                .id(String.valueOf(scheduleEvent.getId()))
                .scheduleEvent(scheduleEvent != null ? mapScheduleEventToDto(scheduleEvent) : null)
                .user(user != null ? mapUserToDto(user) : null)
                .status(scheduleEventAttendee.getStatus())
                .createdDate(scheduleEventAttendee.getCreatedDate())
                .lastModifiedDate(scheduleEventAttendee.getLastModifiedDate())
                .build();
    }

    private ScheduleEventResponseDTO mapScheduleEventToDto(ScheduleEvent scheduleEvent) {
        return ScheduleEventResponseDTO.builder()
                .id(String.valueOf(scheduleEvent.getId()))
                .schedule(
                        scheduleEvent.getSchedule() != null
                                ? mapScheduleToDto(scheduleEvent.getSchedule())
                                : null) // Fix Schedule null
                .name(scheduleEvent.getName())
                .description(scheduleEvent.getDescription())
                .location(scheduleEvent.getLocation())
                .startTime(scheduleEvent.getStartTime())
                .endTime(scheduleEvent.getEndTime())
                .isRecurring(scheduleEvent.isRecurring())
                .recurrenceRule(scheduleEvent.getRecurrenceRule())
                .eventLabel(scheduleEvent.getEventLabel())
                .createdDate(scheduleEvent.getCreatedDate())
                .lastModifiedDate(scheduleEvent.getLastModifiedDate())
                .createdBy(
                        scheduleEvent.getCreatedBy() != null
                                ? scheduleEvent.getCreatedBy().getUsername()
                                : null)
                .fileUrls(mapFileUrls(scheduleEvent))
                .build();
    }

    private ScheduleResponseDTO mapScheduleToDto(Schedule schedule) {
        return ScheduleResponseDTO.builder()
                .id(String.valueOf(schedule.getId()))
                .teamId(String.valueOf(schedule.getTeam().getId()))
                .name(schedule.getName())
                .description(schedule.getDescription())
                .createdDate(schedule.getCreatedDate())
                .lastModifiedDate(schedule.getLastModifiedDate())
                .createdBy(schedule.getCreatedBy().getUsername())
                .build();
    }

    private UserResponse mapUserToDto(User user) {
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    private Set<RoleResponse> mapUserRoles(Set<UserRole> userRoles) {
        return userRoles != null
                ? userRoles.stream()
                        .map(userRole -> RoleResponse.builder()
                                .id(String.valueOf(userRole.getRole().getId()))
                                .name(userRole.getRole().getName())
                                .description(
                                        userRole.getRole().getDescription()) // Ensure Role entity has getDescription()
                                .createdDate(userRole.getRole().getCreatedDate())
                                .lastModifiedDate(userRole.getRole().getLastModifiedDate())
                                .createdByUserId(
                                        userRole.getRole().getCreatedBy() != null
                                                ? String.valueOf(userRole.getRole()
                                                        .getCreatedBy()
                                                        .getId())
                                                : null)
                                .permissions(mapPermissions(userRole.getRole().getPermissions())) // Convert permissions
                                .build())
                        .collect(Collectors.toSet())
                : Collections.emptySet();
    }

    private Set<PermissionResponse> mapPermissions(Set<Permission> permissions) {
        return permissions != null
                ? permissions.stream()
                        .map(permission -> PermissionResponse.builder()
                                .id(String.valueOf(permission.getId()))
                                .name(permission.getName())
                                .apiPath(permission.getApiPath())
                                .method(permission.getMethod())
                                .module(permission.getModule())
                                .build())
                        .collect(Collectors.toSet())
                : Collections.emptySet();
    }

    private List<String> mapFileUrls(ScheduleEvent scheduleEvent) {
        return scheduleEvent.getFileUrls() != null
                ? scheduleEvent.getFileUrls().stream()
                        .map(file -> file.getFileUrl())
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
