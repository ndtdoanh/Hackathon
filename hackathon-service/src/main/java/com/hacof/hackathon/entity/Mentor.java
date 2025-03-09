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
@Table(name = "Mentors")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String expertise;
    String bio;
    String availability;
    Float rating;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    Campus campus;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Team> teams;
}
