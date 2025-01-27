package com.hacof.hackathon.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.hacof.hackathon.constant.RoundType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CompetitionRounds")
public class CompetitionRound extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoundType name;

    private String description;
    private Date startDate;
    private Date endDate;
    private Integer maxTeam;
    private Boolean isVideoRound;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    @ManyToMany
    @JoinTable(
            name = "round_judges",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "judge_id"))
    private List<Judge> judges;
}
