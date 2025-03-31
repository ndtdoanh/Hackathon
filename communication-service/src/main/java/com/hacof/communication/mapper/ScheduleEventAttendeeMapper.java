package com.hacof.communication.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hacof.communication.constant.ScheduleEventStatus;
import com.hacof.communication.dto.request.ScheduleEventAttendeeRequestDTO;
import com.hacof.communication.dto.response.*;
import com.hacof.communication.dto.response.RoleResponse;
import com.hacof.communication.entity.*;

@Component
public class ScheduleEventAttendeeMapper {

    // Chuyển từ ScheduleEventAttendeeRequestDTO sang ScheduleEventAttendee entity
    public ScheduleEventAttendee toEntity(
            ScheduleEventAttendeeRequestDTO requestDTO, ScheduleEvent scheduleEvent, User user) {
        return ScheduleEventAttendee.builder()
                .scheduleEvent(scheduleEvent)
                .user(user)
                .status(ScheduleEventStatus.INVITED) // Mặc định là INVITED
                .build();
    }

    // Convert từ ScheduleEventAttendee entity sang ScheduleEventAttendeeResponseDTO
    public ScheduleEventAttendeeResponseDTO toDto(ScheduleEventAttendee scheduleEventAttendee) {
        if (scheduleEventAttendee == null) {
            return null;
        }

        ScheduleEvent scheduleEvent = scheduleEventAttendee.getScheduleEvent();
        User user = scheduleEventAttendee.getUser();

        return ScheduleEventAttendeeResponseDTO.builder()
                .id(String.valueOf(scheduleEvent.getId()))
                .scheduleEvent(scheduleEvent != null ? mapScheduleEventToDto(scheduleEvent) : null)
                .user(user != null ? mapUserToDto(user) : null) // Trả về toàn bộ thông tin UserResponse
                .status(scheduleEventAttendee.getStatus())
                .createdDate(scheduleEventAttendee.getCreatedDate())
                .lastModifiedDate(scheduleEventAttendee.getLastModifiedDate())
                .build();
    }

    // Chuyển đổi ScheduleEvent sang ScheduleEventResponseDTO
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

    // Chuyển đổi User sang UserResponse
    private UserResponse mapUserToDto(User user) {
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    // Ánh xạ danh sách roles từ UserRole sang RoleResponse
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

    // Ánh xạ fileUrls từ ScheduleEvent để tránh null
    private List<String> mapFileUrls(ScheduleEvent scheduleEvent) {
        return scheduleEvent.getFileUrls() != null
                ? scheduleEvent.getFileUrls().stream()
                        .map(file -> file.getFileUrl())
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
