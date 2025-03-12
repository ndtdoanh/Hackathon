package com.hacof.identity.entity;

import com.hacof.identity.constant.RoundStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rounds")
public class Round {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    RoundStatus status;

    // Submissions related to this round
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Submission> submissions;

    // Criteria used to judge this round
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    List<RoundMarkCriterion> roundMarkCriteria;

    // Judges assigned to this round
    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    List<JudgeRound> judgeRounds;
}

