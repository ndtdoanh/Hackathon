package com.hacof.hackathon.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "judge_rounds")
public class JudgeRound extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "judge_id")
    User judge;

    @ManyToOne
    @JoinColumn(name = "round_id")
    Round round;

    @Column(name = "is_deleted")
    boolean isDeleted = false;
}
