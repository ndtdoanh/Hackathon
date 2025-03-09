package com.hacof.hackathon.entity;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Teams")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Team extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    String name;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathonId;

    @ManyToOne
    @JoinColumn(name = "competition_round_id")
    CompetitionRound competitionRound;

    @OneToOne
    @JoinColumn(name = "leader_id", unique = true)
    private User leader;

    @ManyToOne
    @JoinColumn(name = "mentor_id") // each team has one mentor
    private Mentor mentor;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members;

    boolean passed;
}
