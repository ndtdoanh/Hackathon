package com.hacof.hackathon.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import com.hacof.hackathon.constant.RoundType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "CompetitionRounds")
public class CompetitionRound extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Enumerated(EnumType.STRING)
    RoundType name;

    String description;
    LocalDateTime startDate;
    LocalDateTime endDate;
    int maxTeam;
    boolean isVideoRound = false;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    @ManyToMany
    @JoinTable(
            name = "round_judges",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "judge_id"))
    private List<Judge> judges;

    @OneToMany(mappedBy = "competitionRound", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resource> resources;
}
