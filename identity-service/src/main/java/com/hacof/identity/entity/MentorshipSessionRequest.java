package com.hacof.identity.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.hacof.identity.constant.Status;

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
    @JoinColumn(name = "mentor_team_id")
    MentorTeam mentorTeam;

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
