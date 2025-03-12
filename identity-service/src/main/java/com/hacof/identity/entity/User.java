package com.hacof.identity.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.hacof.identity.constant.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

// src/main/java/com/hacof/identity/entity/User.java
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String email;

    @Column(name = "password")
    String password;

    String firstName;
    String lastName;

    @Column(name = "temp_email")
    String tempEmail;

    @ColumnDefault("0")
    @Column(name = "is_verified")
    boolean isVerified = false;

    public Boolean getIsVerified() {
        return isVerified;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status = Status.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    User createdBy;

    @OneToMany(mappedBy = "createdBy")
    List<User> createdUsers;

    // User Roles
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<UserRole> userRoles = new HashSet<>();

    // User-Hackathon mapping
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<UserHackathon> userHackathons = new HashSet<>();

    // User-Team mapping
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<UserTeam> userTeams = new HashSet<>();

    // Organized Hackathons
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Hackathon> organizedHackathons;

    // Team Leader of multiple teams
    @OneToMany(mappedBy = "teamLeader", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Team> ledTeams;

    // Mentorship Requests
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MentorshipRequest> createdMentorshipRequests;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MentorshipRequest> mentorshipRequestsAsMentor;

    @OneToMany(mappedBy = "evaluatedBy", cascade = CascadeType.ALL)
    List<MentorshipRequest> evaluatedMentorshipRequests;

    // Mentorship Session Requests
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MentorshipSessionRequest> createdMentorshipSessionRequests;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<MentorshipSessionRequest> mentorshipSessionRequestsAsMentor;

    @OneToMany(mappedBy = "evaluatedBy", cascade = CascadeType.ALL)
    List<MentorshipSessionRequest> evaluatedMentorshipSessionRequests;

    // Judge in Hackathon Rounds
    @OneToMany(mappedBy = "judge", cascade = CascadeType.ALL, orphanRemoval = true)
    List<JudgeRound> judgeRounds;

    // Judge Submissions
    @OneToMany(mappedBy = "judge", cascade = CascadeType.ALL, orphanRemoval = true)
    List<JudgeSubmission> judgeSubmissions;

    // Team Requests & Individual Registration Requests
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamRequest> teamRequests;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<IndividualRegistrationRequest> individualRegistrationRequests;
}