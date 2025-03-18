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
@Table(name = "judge_submission_details")
public class JudgeSubmissionDetail extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "judge_submission_id")
    JudgeSubmission judgeSubmission;

    @ManyToOne
    @JoinColumn(name = "round_mark_criterion_id")
    RoundMarkCriterion roundMarkCriterion;

    int score;
    String note;
}
