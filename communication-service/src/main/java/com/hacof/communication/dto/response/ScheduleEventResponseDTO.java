package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleEventResponseDTO {

    String id; // ID của ScheduleEvent
    ScheduleResponseDTO schedule; // ID của Schedule mà ScheduleEvent này thuộc về
    String name; // Tên của ScheduleEvent
    String description; // Mô tả về ScheduleEvent
    String location; // Vị trí của ScheduleEvent
    LocalDateTime startTime; // Thời gian bắt đầu
    LocalDateTime endTime; // Thời gian kết thúc
    boolean isRecurring; // Nếu sự kiện là định kỳ
    String recurrenceRule; // Quy tắc định kỳ
    LocalDateTime createdDate; // Thời gian tạo
    LocalDateTime lastModifiedDate; // Thời gian sửa đổi
    String createdBy; // Người tạo
    List<String> fileUrls; // List of URLs associated with the event
}
