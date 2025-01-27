package com.hacof.hackathon.entity;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Campuses")
public class Campus extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @OneToMany(mappedBy = "campus")
    private List<Event> events;

    @OneToMany(mappedBy = "campus")
    private List<Mentor> mentors;

    @OneToMany(mappedBy = "campus")
    private List<TrainingSession> trainingSessions;
}
