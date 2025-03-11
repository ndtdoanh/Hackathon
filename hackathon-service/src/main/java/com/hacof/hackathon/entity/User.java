package com.hacof.hackathon.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    String firstName;
    String lastName;
    private String email;
    private Boolean isVerified;
    private String status;

    @ManyToMany
    Set<Role> roles;

    @OneToMany(mappedBy = "organizer")
    private List<Hackathon> organizedHackathons;

    @ManyToOne
    @JoinColumn(name = "team_id", unique = true)
    private Team team;
}
