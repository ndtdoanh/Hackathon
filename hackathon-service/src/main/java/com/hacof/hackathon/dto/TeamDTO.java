package com.hacof.hackathon.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamDTO {
    String id;
    String name;
    UserDTO teamLeaderId;
    Set<UserTeamDTO> teamMembers;

    @JsonIgnore
    String bio;

    boolean isDeleted;

    @JsonIgnore
    String deletedById;

    List<TeamHackathonDTO> teamHackathons;

    @JsonIgnore
    List<MentorshipSessionRequestDTO> mentorshipSessionRequests;

    @JsonIgnore
    List<TeamRoundDTO> teamRounds;

    @JsonIgnore
    List<HackathonResultDTO> hackathonResults;

    @JsonIgnore
    List<MentorshipRequestDTO> mentorshipRequests;

    @JsonIgnore
    List<FeedbackDTO> feedbacks;

    @JsonIgnore
    List<MentorTeamDTO> mentorTeams;

    @JsonIgnore
    List<MentorTeamLimitDTO> mentorTeamLimits;

    @JsonIgnore
    List<SubmissionDTO> submissions;
}
