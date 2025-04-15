package com.hacof.hackathon.dto;

import java.time.LocalDateTime;

import com.hacof.hackathon.constant.TeamRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestSearchDTO {
    private String hackathonId;
    private String teamName;
    private TeamRequestStatus status;
    private String memberId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDirection;
}
