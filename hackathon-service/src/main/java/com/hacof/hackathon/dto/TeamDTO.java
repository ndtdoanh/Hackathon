package com.hacof.hackathon.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeamDTO {
    private Long id;
    private String name;
    private Long hackathonId;
    private List<Long> memberIds;
}
