package com.hacof.hackathon.entity;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.hacof.hackathon.constant.TeamRequestStatus;

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
@Table(name = "team_requests")
public class TeamRequest extends AuditUserBase {
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

    // @JoinColumn(name = "confirmation_deadline")
    @Column(name = "confirmation_deadline")
    LocalDateTime confirmationDeadline;

    // @JoinColumn(name = "note")
    @Column(name = "note")
    String note;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    User reviewedBy;

    @JoinColumn(name = "reviewed_at")
    LocalDateTime reviewedAt;

    @OneToMany(mappedBy = "teamRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamRequestMember> teamRequestMembers = new ArrayList<>();
}
