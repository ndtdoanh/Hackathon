package com.hacof.hackathon.entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.hackathon.constant.Name;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "CompetitionRounds")
public class CompetitionRound extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    Name name;

    @Lob
    @Column(name = "description")
    String description;

    @NotNull
    @Column(name = "start_date")
    Instant startDate;

    @NotNull
    @Column(name = "end_date")
    Instant endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    @NotNull
    @Column(name = "max_team")
    Integer maxTeam;

    @ColumnDefault("0")
    @Column(name = "is_video_round")
    Boolean isVideoRound;

    @ManyToMany
    List<Judge> judges;

    @ManyToMany
    List<Mentor> mentors;

    @OneToMany(mappedBy = "competitionRound", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Resource> resources;
}
