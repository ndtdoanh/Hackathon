package com.hacof.communication.dto.response;

import java.time.LocalDateTime;

import com.hacof.communication.constant.ScheduleEventStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEventAttendeeResponseDTO {

    String id;
    //    ScheduleEventResponseDTO scheduleEvent;
    //    UserResponse user;
    String scheduleEventId;
    String userId;
    ScheduleEventStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
