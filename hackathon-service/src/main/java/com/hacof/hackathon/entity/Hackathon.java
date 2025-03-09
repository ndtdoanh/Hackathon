package com.hacof.hackathon.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Hackathons")
public class Hackathon extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String bannerImageUrl;
    private String description;

    @Column(name = "start_date", columnDefinition = "datetime(6)", nullable = false)
    private LocalDateTime startDate; // example:  2024-02-16 12:34:56.123456. -> datetime(6)

    @Column(name = "end_date", columnDefinition = "datetime(6)", nullable = false)
    private LocalDateTime endDate;

    private int maxTeams;
    private int minTeamSize;
    private int maxTeamSize;
    private String status;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetitionRound> rounds;
}
