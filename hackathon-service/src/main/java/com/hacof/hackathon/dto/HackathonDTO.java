package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.hacof.hackathon.constant.Status;
import com.hacof.hackathon.entity.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HackathonDTO extends AuditBaseDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Banner image url is mandatory")
    private String bannerImageUrl;

    private String description;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime startDate;

    @NotNull(message = "Start date is mandatory")
    private LocalDateTime endDate;

    @NotNull(message = "Number round is mandatory")
    int numberRound;

    @NotNull(message = "Max teams is mandatory")
    int maxTeams;

    @NotNull(message = "Min team size is mandatory")
    int minTeamSize;

    @NotNull(message = "Max team size is mandatory")
    int maxTeamSize;

    @NotNull(message = "Status is mandatory")
    Status status;

    List<RoundDTO> rounds;
    List<TeamHackathon> teamHackathons;
    List<HackathonResult> hackathonResults;
    List<UserHackathon> userHackathons;
    List<TeamRequest> teamRequests;
    List<IndividualRegistrationRequest> individualRegistrationRequests;
    List<MentorshipRequest> mentorshipRequests;
    List<MentorshipSessionRequest> mentorshipSessionRequests;
    List<SponsorshipHackathon> sponsorshipHackathons;
    List<Device> devices;
    List<Feedback> feedbacks;
}
