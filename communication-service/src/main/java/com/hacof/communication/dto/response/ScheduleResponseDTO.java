package com.hacof.communication.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {

    private Long id;  // ID của Schedule
    private Long teamId;  // ID của Team liên kết với Schedule
    private String name;  // Tên của Schedule
    private String description;  // Mô tả về Schedule
    private LocalDateTime createdDate;  // Thời gian tạo Schedule
    private LocalDateTime lastModifiedDate;  // Thời gian sửa đổi Schedule
    private String createdBy;  // Người tạo Schedule
    private List<ScheduleEventResponseDTO> scheduleEvents;  // Danh sách các sự kiện liên quan
}
