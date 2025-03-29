package com.hacof.hackathon.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.hacof.hackathon.entity.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HackathonDTO {
    String id;

    String title;
    String subTitle;

    String bannerImageUrl;

    LocalDateTime enrollStartDate;

    LocalDateTime enrollEndDate;

    int enrollmentCount;

    LocalDateTime startDate;

    LocalDateTime endDate;

    String information;
    String description;
    List<String> documentation; // Document public URLs
    String contact;
    String category; // Used for category filtering
    String organization; // Used for organization filtering
    String enrollmentStatus;

    String status;

    int minimumTeamMembers;
    int maximumTeamMembers;

    // List<Round> rounds;
    List<Long> roundIds;
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

    // Audit fields
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
}
