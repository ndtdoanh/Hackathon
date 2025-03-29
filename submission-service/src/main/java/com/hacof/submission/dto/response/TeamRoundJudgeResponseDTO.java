package com.hacof.submission.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRoundJudgeResponseDTO {

    private Long id;              // ID của TeamRoundJudge
    private TeamRoundResponseDTO teamRound; // Thông tin chi tiết về TeamRound
    private UserResponse judge;         // Thông tin chi tiết về Judge
    private String createdBy;     // Người tạo
    private String createdDate;   // Thời gian tạo
    private String lastModifiedDate; // Thời gian sửa đổi
}
