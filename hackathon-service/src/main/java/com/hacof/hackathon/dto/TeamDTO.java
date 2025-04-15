package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamDTO {
    String id;
    String name;
    String teamLeaderId;
    UserDTO teamLeader;
    private Set<UserTeamDTO> teamMembers;

    String bio;

    boolean isDeleted;

    String deletedById;

    UserDTO deletedBy;

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

    String createdByUserName; // save username
    LocalDateTime createdAt;
    String lastModifiedByUserName; // save username
    LocalDateTime updatedAt = LocalDateTime.now();
}
