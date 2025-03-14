package com.hacof.identity.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.hacof.identity.constant.MentorshipStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mentorship_requests")
public class MentorshipRequest extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    User mentor;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    MentorshipStatus status = MentorshipStatus.PENDING;

    @Column(name = "evaluated_at")
    LocalDateTime evaluatedAt;

    @ManyToOne
    @JoinColumn(name = "evaluated_by")
    User evaluatedBy;
}
