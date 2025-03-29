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
    private Long teamRoundId;  // ID of the TeamRound to which this Judge belongs
    private Long judgeId;      // ID of the Judge associated with this TeamRoundJudge
}
