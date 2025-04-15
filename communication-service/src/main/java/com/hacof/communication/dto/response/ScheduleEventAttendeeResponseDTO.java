package com.hacof.communication.dto.response;

import com.hacof.communication.constant.ScheduleEventStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEventAttendeeResponseDTO {

    String id;
    ScheduleEventResponseDTO scheduleEvent;
    UserResponse user;
    ScheduleEventStatus status;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;
}
