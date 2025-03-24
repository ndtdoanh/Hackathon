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

    private Long id;              // ID của ScheduleEventAttendee
    private Long scheduleEventId; // ID của ScheduleEvent mà Attendee này tham gia
    private Long userId;          // ID của User tham gia
    private ScheduleEventStatus status; // Trạng thái tham gia

    // Trường AuditBase
    private LocalDateTime createdDate;  // Thời gian tạo
    private LocalDateTime lastModifiedDate; // Thời gian sửa đổi
}
