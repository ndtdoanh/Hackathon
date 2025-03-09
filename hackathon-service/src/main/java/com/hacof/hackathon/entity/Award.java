package com.hacof.hackathon.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Awards")
public class Award extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Double prizeAmount;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;
}
