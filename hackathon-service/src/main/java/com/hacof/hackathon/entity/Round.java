package com.hacof.hackathon.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hacof.hackathon.constant.RoundStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "rounds")
public class Round extends AuditUserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    @JsonIgnore
    Hackathon hackathon;

    @Column(name = "start_time")
    LocalDateTime startTime;

    @Column(name = "end_time")
    LocalDateTime endTime;

    int roundNumber;

    String roundTitle;

    int totalTeam;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    RoundStatus status;

    // Submissions related to this round
    @OneToMany(
            mappedBy = "round",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<Submission> submissions = new ArrayList<>();

    // Criteria used to judge this round
    @OneToMany(
            mappedBy = "round",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<RoundMarkCriterion> roundMarkCriteria = new ArrayList<>();

    // Judges assigned to this round
    @OneToMany(
            mappedBy = "round",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<JudgeRound> judgeRounds = new ArrayList<>();

    // Teams that participated in this round
    @OneToMany(
            mappedBy = "round",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<TeamRound> teamRounds = new ArrayList<>();

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    //    @JsonManagedReference
    List<RoundLocation> roundLocations;
}
