package com.hacof.communication.dto.response;

import com.hacof.communication.constant.ScheduleEventStatus;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventAttendeeResponseDTO {

    private Long id;
    private ScheduleEventResponseDTO scheduleEvent;
    private UserResponse user;
    private ScheduleEventStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
