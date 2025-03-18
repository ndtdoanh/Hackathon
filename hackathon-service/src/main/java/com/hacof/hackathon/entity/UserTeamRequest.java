package com.hacof.hackathon.entity;

import jakarta.persistence.*;

import com.hacof.hackathon.constant.Status;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_team_requests")
public class UserTeamRequest extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "request_type")
    private String requestType; // INVITATION, JOIN_REQUEST

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status; // PENDING, APPROVED, REJECTED
}
