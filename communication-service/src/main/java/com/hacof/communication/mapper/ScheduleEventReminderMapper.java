package com.hacof.communication.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.communication.dto.request.ScheduleEventReminderRequestDTO;
import com.hacof.communication.dto.response.PermissionResponse;
import com.hacof.communication.dto.response.RoleResponse;
import com.hacof.communication.dto.response.ScheduleEventReminderResponseDTO;
import com.hacof.communication.dto.response.ScheduleEventResponseDTO;
import com.hacof.communication.dto.response.ScheduleResponseDTO;
import com.hacof.communication.dto.response.UserResponse;
import com.hacof.communication.entity.Permission;
import com.hacof.communication.entity.Schedule;
import com.hacof.communication.entity.ScheduleEvent;
import com.hacof.communication.entity.ScheduleEventReminder;
import com.hacof.communication.entity.User;
import com.hacof.communication.entity.UserRole;

@Component
public class ScheduleEventReminderMapper {

    public ScheduleEventReminder toEntity(
            ScheduleEventReminderRequestDTO requestDTO, ScheduleEvent scheduleEvent, User user) {
        return ScheduleEventReminder.builder()
                .scheduleEvent(scheduleEvent)
                .user(user)
                .remindAt(requestDTO.getRemindAt())
                .build();
    }

    public ScheduleEventReminderResponseDTO toDto(ScheduleEventReminder scheduleEventReminder) {
        if (scheduleEventReminder == null) {
            return null;
        }

        ScheduleEvent scheduleEvent = scheduleEventReminder.getScheduleEvent();
        User user = scheduleEventReminder.getUser();

        return ScheduleEventReminderResponseDTO.builder()
                .id(String.valueOf(scheduleEventReminder.getId()))
                .scheduleEventId(String.valueOf(scheduleEventReminder.getScheduleEvent().getId()))
                .userId(String.valueOf(scheduleEventReminder.getUser().getId()))
                .remindAt(scheduleEventReminder.getRemindAt())
                .createdAt(scheduleEventReminder.getCreatedDate())
                .updatedAt(scheduleEventReminder.getLastModifiedDate())
                .build();
    }

//    private ScheduleEventResponseDTO mapScheduleEventToDto(ScheduleEvent scheduleEvent) {
//        return ScheduleEventResponseDTO.builder()
//                .id(String.valueOf(scheduleEvent.getId()))
//                .schedule(scheduleEvent.getSchedule() != null ? mapScheduleToDto(scheduleEvent.getSchedule()) : null)
//                .name(scheduleEvent.getName())
//                .description(scheduleEvent.getDescription())
//                .location(scheduleEvent.getLocation())
//                .startTime(scheduleEvent.getStartTime())
//                .endTime(scheduleEvent.getEndTime())
//                .isRecurring(scheduleEvent.isRecurring())
//                .recurrenceRule(scheduleEvent.getRecurrenceRule())
//                .eventLabel(scheduleEvent.getEventLabel())
//                .createdDate(scheduleEvent.getCreatedDate())
//                .lastModifiedDate(scheduleEvent.getLastModifiedDate())
//                .createdBy(
//                        scheduleEvent.getCreatedBy() != null
//                                ? scheduleEvent.getCreatedBy().getUsername()
//                                : null)
//                .fileUrls(mapFileUrls(scheduleEvent))
//                .build();
//    }

    private ScheduleResponseDTO mapScheduleToDto(Schedule schedule) {
        return ScheduleResponseDTO.builder()
                .id(String.valueOf(schedule.getId()))
                .teamId(
                        schedule.getTeam() != null
                                ? String.valueOf(schedule.getTeam().getId())
                                : null)
                .name(schedule.getName())
                .description(schedule.getDescription())
                .createdDate(schedule.getCreatedDate())
                .lastModifiedDate(schedule.getLastModifiedDate())
                .createdBy(
                        schedule.getCreatedBy() != null
                                ? schedule.getCreatedBy().getUsername()
                                : null)
                .build();
    }

    private UserResponse mapUserToDto(User user) {
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    private Set<RoleResponse> mapUserRoles(Set<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> RoleResponse.builder()
                        .id(String.valueOf(userRole.getRole().getId())) // Chuyển đổi long -> String
                        .name(userRole.getRole().getName())
                        .description(userRole.getRole().getDescription())
                        .createdDate(userRole.getRole().getCreatedDate())
                        .lastModifiedDate(userRole.getRole().getLastModifiedDate())
                        .createdByUserId(
                                userRole.getRole().getCreatedBy() != null
                                        ? String.valueOf(userRole.getRole()
                                                .getCreatedBy()
                                                .getId())
                                        : null)
                        .permissions(
                                userRole.getRole().getPermissions() != null
                                        ? mapPermissions(userRole.getRole().getPermissions())
                                        : Collections.emptySet())
                        .build())
                .collect(Collectors.toSet());
    }

    private Set<PermissionResponse> mapPermissions(Set<Permission> permissions) {
        return permissions.stream()
                .map(permission -> PermissionResponse.builder()
                        .id(String.valueOf(permission.getId())) // Chuyển đổi long -> String
                        .name(permission.getName())
                        .apiPath(permission.getApiPath())
                        .method(permission.getMethod())
                        .module(permission.getModule())
                        .build())
                .collect(Collectors.toSet());
    }

    private List<String> mapFileUrls(ScheduleEvent scheduleEvent) {
        return scheduleEvent.getFileUrls() != null
                ? scheduleEvent.getFileUrls().stream()
                        .map(file -> file.getFileUrl())
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
