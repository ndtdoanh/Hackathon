package com.hacof.communication.dto.request;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEventRequestDTO {

    String scheduleId; // ID của Schedule mà ScheduleEvent này thuộc về
    String name; // Tên của ScheduleEvent
    String description; // Mô tả về ScheduleEvent
    String location; // Vị trí của ScheduleEvent
    LocalDateTime startTime; // Thời gian bắt đầu
    LocalDateTime endTime; // Thời gian kết thúc
    boolean isRecurring; // Nếu sự kiện là định kỳ
    String recurrenceRule; // Quy tắc định kỳ
}
