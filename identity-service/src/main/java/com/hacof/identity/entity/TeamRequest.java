package com.hacof.identity.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import com.hacof.identity.constant.TeamRequestStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "team_requests")
public class TeamRequest extends AuditCreatedBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    @Column(name = "name")
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    TeamRequestStatus status;

    @JoinColumn(name = "confirmation_deadline")
    LocalDateTime confirmationDeadline;

    @JoinColumn(name = "note")
    String note;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    User reviewedBy;

    @OneToMany(mappedBy = "teamRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamRequestMember> teamRequestMembers;
}
