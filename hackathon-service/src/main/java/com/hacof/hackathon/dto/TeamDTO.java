package com.hacof.hackathon.dto;

import java.util.List;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamDTO {
    String id;
    String name;
    String hackathonId;
    String teamLeaderId;
    Set<UserTeamDTO> teamMembers;
    List<MentorshipSessionRequestDTO> mentorshipSessionRequests;
    List<TeamHackathonDTO> teamHackathons;
    List<TeamRoundDTO> teamRounds;
    List<HackathonResultDTO> hackathonResults;
    List<MentorshipRequestDTO> mentorshipRequests;
    List<FeedbackDTO> feedbacks;
    String bio;
    boolean isDeleted;
    String deletedById;
}
