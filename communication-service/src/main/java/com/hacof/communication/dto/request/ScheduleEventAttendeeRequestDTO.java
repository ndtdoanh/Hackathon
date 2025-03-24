package com.hacof.communication.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventAttendeeRequestDTO {

    private Long scheduleEventId;
    private Long userId;
}
