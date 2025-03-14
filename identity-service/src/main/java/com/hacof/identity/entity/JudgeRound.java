package com.hacof.identity.entity;

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
    @JoinColumn(name = "judge_id", nullable = false)
    User judge;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    Round round;

    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;
}
