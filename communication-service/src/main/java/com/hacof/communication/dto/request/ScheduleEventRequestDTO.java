package com.hacof.communication.dto.request;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventRequestDTO {

    private Long scheduleId; // ID của Schedule mà ScheduleEvent này thuộc về
    private String name; // Tên của ScheduleEvent
    private String description; // Mô tả về ScheduleEvent
    private String location; // Vị trí của ScheduleEvent
    private LocalDateTime startTime; // Thời gian bắt đầu
    private LocalDateTime endTime; // Thời gian kết thúc
    private boolean isRecurring; // Nếu sự kiện là định kỳ
    private String recurrenceRule; // Quy tắc định kỳ
}
