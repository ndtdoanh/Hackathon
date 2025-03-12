package com.hacof.identity.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.hacof.identity.constants.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    boolean isVerified = false;

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
