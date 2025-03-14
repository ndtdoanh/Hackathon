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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User extends AuditUserBase {
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
    List<Team> leadTeams;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ConversationUser> conversationUsers;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> sentMessages;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Board> boards;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BoardList> boardLists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BoardUser> boardUsers;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> tasks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskAssignee> taskAssignees;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TaskComment> taskComments;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Schedule> schedules;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ScheduleEvent> scheduleEvents;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ScheduleEventAttendee> scheduleEventAttendees;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserDevice> userDevices;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BlogPost> blogPosts;

    @OneToMany(mappedBy = "reviewedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BlogPost> reviewedBlogPosts;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Sponsorship> sponsorships;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ThreadPostReport> reportedThreadPosts;

    @OneToMany(mappedBy = "reviewedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ThreadPostReport> reviewedThreadReports;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Feedback> receivedFeedbacks;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Feedback> createdFeedbacks;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Notification> receivedNotifications;

    public void addRole(Role role) {
        if (this.userRoles == null) {
            this.userRoles = new HashSet<>();
        }

        boolean alreadyHasRole =
                this.userRoles.stream().anyMatch(userRole -> userRole.getRole().equals(role));

        if (!alreadyHasRole) {
            UserRole userRole = new UserRole(this, role);
            this.userRoles.add(userRole);
        }
    }
}
