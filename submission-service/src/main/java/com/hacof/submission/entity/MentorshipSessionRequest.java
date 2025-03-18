package com.hacof.submission.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.hacof.submission.constant.Status;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mentorship_session_requests")
public class MentorshipSessionRequest extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    User mentor;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;

    LocalDateTime startTime;
    LocalDateTime endTime;

    String location;
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;

    @ManyToOne
    @JoinColumn(name = "evaluated_by")
    User evaluatedBy;

    LocalDateTime evaluatedAt;
}
