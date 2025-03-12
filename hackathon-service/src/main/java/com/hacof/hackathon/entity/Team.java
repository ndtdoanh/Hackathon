package com.hacof.hackathon.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.hackathon.constant.Status;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Teams")
public class Team extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hackathon_id", nullable = false)
    Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "competition_round_id")
    CompetitionRound competitionRound;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_leader_id", nullable = false)
    User teamLeader;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "campus_id", nullable = false)
    Campus campus;

    @Lob
    @Column(name = "bio")
    String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status = Status.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "mentor_id") // each team has one mentor
    Mentor mentor;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    List<User> members;

    boolean passed;
}
