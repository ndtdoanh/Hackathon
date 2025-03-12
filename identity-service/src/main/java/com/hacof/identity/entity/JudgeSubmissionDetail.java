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
@Table(name = "judge_submission_details")
public class JudgeSubmissionDetail extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "judge_submission_id", nullable = false)
    JudgeSubmission judgeSubmission;

    @ManyToOne
    @JoinColumn(name = "round_mark_criterion_id", nullable = false)
    RoundMarkCriterion roundMarkCriterion;

    @Column(nullable = false)
    int score;

    @Column(length = 500)
    String note;
}
