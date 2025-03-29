package com.hacof.communication.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.communication.constant.TeamRoundStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "team_rounds")
public class TeamRound extends AuditCreatedBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_id")
    Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "round_id")
    Round round;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TeamRoundStatus status;

    @Column(nullable = false)
    String description;

    @OneToMany(mappedBy = "teamRound", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamRoundJudge> teamRoundJudges;
}
