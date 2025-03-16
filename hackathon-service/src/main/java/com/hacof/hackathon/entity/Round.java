package com.hacof.hackathon.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import com.hacof.hackathon.constant.RoundName;
import com.hacof.hackathon.constant.RoundStatus;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    RoundName roundName;

    @Column(name = "start_time")
    LocalDateTime startTime;

    @Column(name = "end_time")
    LocalDateTime endTime;

    String description;

    int maxTeam;

    boolean isVideoRound = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    RoundStatus status;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

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
    List<RoundCampus> roundCampuses;
}
