package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hacof.hackathon.entity.User;
import com.hacof.hackathon.util.CustomLocalDateTimeDeserialized;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HackathonDTO extends AuditBaseDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Banner image URL is mandatory")
    private String bannerImageUrl;

    private String description;

    @NotNull(message = "Start date is mandatory")
    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    private LocalDateTime startDate;

    @NotNull(message = "Start date is mandatory")
    @JsonDeserialize(using = CustomLocalDateTimeDeserialized.class)
    private LocalDateTime endDate;

    private int maxTeams;

    private int minTeamSize;

    private int maxTeamSize;

    @NotNull(message = "Organizer ID is mandatory")
    private User organizerId;

    private String status;

    private List<Long> eventIds;

    //    private Long organizerId;
    //    private String organizerName;
    //    private List<CompetitionRoundDTO> rounds;
    //    private List<EventDTO> events;
    //    private List<JudgeDTO> judges;
    //    // private List<CheckInDTO> checkIns;
    //    private List<ResourceDTO> resources;
}
