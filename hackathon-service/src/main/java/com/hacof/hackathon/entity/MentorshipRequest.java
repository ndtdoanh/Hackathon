package com.hacof.hackathon.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.hacof.hackathon.constant.MentorshipStatus;

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
@Table(name = "mentorship_requests")
public class MentorshipRequest extends AuditUserBase {

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
