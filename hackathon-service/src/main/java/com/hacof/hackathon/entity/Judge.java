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
@Table(name = "Judges")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Judge extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String email;

    @ManyToMany(mappedBy = "judges")
    List<CompetitionRound> rounds;
}
