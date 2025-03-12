package com.hacof.identity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.Instant;

import com.hacof.identity.constant.MentorshipStatus;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mentorship_requests")
public class MentorshipRequest extends AuditBase {

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

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    User createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    MentorshipStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @Column(name = "updated_at")
    Instant updatedAt;

    @Column(name = "evaluated_at")
    Instant evaluatedAt;

    @ManyToOne
    @JoinColumn(name = "evaluated_by")
    User evaluatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        status = MentorshipStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
