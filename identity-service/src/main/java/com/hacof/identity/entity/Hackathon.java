package com.hacof.identity.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.hacof.identity.constant.CategoryStatus;
import com.hacof.identity.constant.OrganizationStatus;
import com.hacof.identity.constant.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "hackathons")
public class Hackathon extends AuditUserBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "title")
    String title;

    @Column(name = "sub_title")
    String subTitle;

    @Column(name = "banner_image_url")
    String bannerImageUrl;

    @Column(name = "enroll_start_date", columnDefinition = "datetime(6)")
    LocalDateTime enrollStartDate;

    @Column(name = "enroll_end_date", columnDefinition = "datetime(6)")
    LocalDateTime enrollEndDate;

    @Lob
    @Column(name = "description")
    String description;

    @Lob
    @Column(name = "information")
    String information;

    @Column(name = "start_date", columnDefinition = "datetime(6)")
    LocalDateTime startDate; // example:  2024-02-16 12:34:56.123456. -> datetime(6)

    @Column(name = "end_date", columnDefinition = "datetime(6)")
    LocalDateTime endDate;

    @Column(name = "max_teams")
    int maxTeams;

    @Column(name = "min_team_size")
    int minTeamSize;

    @Column(name = "max_team_size")
    int maxTeamSize;

    @Column(name = "contact")
    String contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    CategoryStatus category;

    @Enumerated(EnumType.STRING)
    @Column(name = "organization")
    OrganizationStatus organization;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status; // DRAFT - OPEN - ON_GOING - CLOSED

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<Round> rounds = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<TeamHackathon> teamHackathons = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<HackathonResult> hackathonResults = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<UserHackathon> userHackathons = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<TeamRequest> teamRequests = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<IndividualRegistrationRequest> individualRegistrationRequests = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<MentorshipRequest> mentorshipRequests = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<SponsorshipHackathon> sponsorshipHackathons = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<Device> devices = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<Conversation> conversations;

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<Board> boards;

    @OneToMany(
            mappedBy = "hackathon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<FileUrl> documentation = new ArrayList<>();
}
