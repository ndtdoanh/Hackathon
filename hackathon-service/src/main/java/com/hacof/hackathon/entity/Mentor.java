package com.hacof.hackathon.entity;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Mentors")
public class Mentor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String expertise;
    private String bio;
    private String availability;
    private Float rating;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    private Campus campus;

    @ManyToMany(mappedBy = "mentors")
    private List<Team> teams;
}
