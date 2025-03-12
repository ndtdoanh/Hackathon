package com.hacof.hackathon.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.ColumnDefault;

import com.hacof.hackathon.constant.Status;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Users")
public class User extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "username", nullable = false, unique = true)
    String username;

    @Column(name = "email", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String email;

    @Column(name = "password")
    String password;

    String firstName;
    String lastName;

    @Column(name = "temp_email")
    String tempEmail;

    @ColumnDefault("0")
    @Column(name = "is_verified")
    Boolean isVerified = false;

    public Boolean getIsVerified() {
        return isVerified;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status = Status.ACTIVE;

    @ManyToMany
    Set<Role> roles;

    @OneToMany(mappedBy = "organizer")
    List<Hackathon> organizedHackathons;

    @ManyToOne
    @JoinColumn(name = "team_id", unique = true)
    Team team;
}
