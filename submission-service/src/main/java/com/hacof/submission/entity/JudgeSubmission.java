package com.hacof.submission.entity;

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
@Table(name = "judge_submissions")
public class JudgeSubmission extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "judge_id", nullable = false)
    User judge;

    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    Submission submission;

    int score;

    @Column(length = 500)
    String note;

    @OneToMany(mappedBy = "judgeSubmission", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<JudgeSubmissionDetail> judgeSubmissionDetails;
}
