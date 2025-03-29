package com.hacof.submission.dto.response;

import com.hacof.submission.constant.TeamRoundStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TeamRoundResponseDTO {

    private Long id;                  // ID của TeamRound
    private Long teamId;              // ID của Team
    private Long roundId;             // ID của Round
    private TeamRoundStatus status;   // Trạng thái của TeamRound
    private String description;       // Mô tả về TeamRound
    private String createdBy;         // Người tạo TeamRound
    private String createdDate;       // Thời gian tạo
    private String lastModifiedDate;  // Thời gian sửa đổi
}
