package com.hacof.hackathon.entity;

import java.util.Date;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TeamAwards")
public class TeamAward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "award_id")
    private Award award;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private Date awardedAt;
}
