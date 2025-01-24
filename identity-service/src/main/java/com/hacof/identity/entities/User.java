package com.hacof.identity.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.hacof.identity.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //    @Size(max = 50)
    //    @NotNull
    //    @Column(name = "username", nullable = false, length = 50)
    //    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    private String email;

    String firstName;
    String lastName;

    @Column(name = "last_login")
    private Instant lastLogin;

    @ColumnDefault("0")
    @Column(name = "is_verified")
    private Boolean isVerified;

    //    @Size(max = 255)
    //    @Column(name = "refresh_token")
    //    private String refreshToken;
    //
    //    @Column(name = "token_expires_at")
    //    private Instant tokenExpiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;
}
