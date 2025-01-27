package com.hacof.hackathon.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JudgeAssignments")
public class JudgeAssignment extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "judge_id")
    private User judge;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private CompetitionRound round;
}
