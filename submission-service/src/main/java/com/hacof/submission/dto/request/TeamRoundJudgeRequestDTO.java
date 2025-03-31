package com.hacof.submission.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamRoundJudgeRequestDTO {
    private Long teamRoundId;
    private Long judgeId;
}
