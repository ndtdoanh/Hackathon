package com.hacof.hackathon.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeamDTO {
    private long id;
    private String name;
    private String bio;
    private long hackathonId;
    private long teamLeaderId;
    private List<UserTeamDTO> teamMembers;
}
