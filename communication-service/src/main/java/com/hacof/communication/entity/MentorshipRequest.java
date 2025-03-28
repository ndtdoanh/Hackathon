package com.hacof.communication.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.hacof.communication.constant.MentorshipStatus;

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
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    User mentor;

    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    MentorshipStatus status;

    @Column(name = "evaluated_at")
    LocalDateTime evaluatedAt;

    @ManyToOne
    @JoinColumn(name = "evaluated_by")
    User evaluatedBy;
}
