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
public class ScheduleResponseDTO {

    String id; // ID của Schedule
    String teamId; // ID của Team liên kết với Schedule
    String name; // Tên của Schedule
    String description; // Mô tả về Schedule
    LocalDateTime createdDate; // Thời gian tạo Schedule
    LocalDateTime lastModifiedDate; // Thời gian sửa đổi Schedule
    String createdBy; // Người tạo Schedule
    List<ScheduleEventResponseDTO> scheduleEvents; // Danh sách các sự kiện liên quan
}
