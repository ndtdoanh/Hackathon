package com.hacof.submission.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import com.hacof.submission.constant.RoundStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rounds")
public class Round extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    Hackathon hackathon;

    @Column(name = "start_time", nullable = false)
    LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    LocalDateTime endTime;

    int roundNumber;

    String roundTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    RoundStatus status = RoundStatus.UPCOMING;

    // Submissions related to this round
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Submission> submissions;

    // Criteria used to judge this round
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RoundMarkCriterion> roundMarkCriteria;

    // Judges assigned to this round
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    List<JudgeRound> judgeRounds;

    // Teams that participated in this round
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TeamRound> teamRounds;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RoundLocation> roundLocations;
}
