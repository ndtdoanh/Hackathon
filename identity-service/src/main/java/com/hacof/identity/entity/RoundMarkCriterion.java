package com.hacof.identity.entity;

import java.util.Set;

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
@Table(name = "round_mark_criterions")
public class RoundMarkCriterion extends AuditUserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    Round round;

    String name;

    int maxScore;

    String note;

    @OneToMany(mappedBy = "roundMarkCriterion", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<JudgeSubmissionDetail> judgeSubmissionDetails;
}
