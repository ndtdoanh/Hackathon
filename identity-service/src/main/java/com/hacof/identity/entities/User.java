package com.hacof.identity.entities;

import java.time.Instant;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.hacof.identity.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    //    @Column(name = "username", nullable = false)
    //    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String email;

    String firstName;
    String lastName;

    @Column(name = "last_login")
    Instant lastLogin;

    @ColumnDefault("0")
    @Column(name = "is_verified")
    Boolean isVerified;

    //    @Size(max = 255)
    //    @Column(name = "refresh_token")
    //    String refreshToken;
    //
    //    @Column(name = "token_expires_at")
    //    Instant tokenExpiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status = Status.ACTIVE;

    @ManyToMany
    Set<Role> roles;
}
