package com.hacof.submission.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import com.hacof.submission.constant.SponsorshipStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "sponsorships")
public class Sponsorship extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @Column(name = "brand", nullable = false)
    String brand;

    @Lob
    @Column(name = "content")
    String content;

    @NotNull
    @Column(name = "money", nullable = false)
    double money;

    @NotNull
    @Column(name = "time_from", nullable = false)
    LocalDateTime timeFrom;

    @NotNull
    @Column(name = "time_to", nullable = false)
    LocalDateTime timeTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    SponsorshipStatus status;

    @OneToMany(mappedBy = "sponsorship", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<SponsorshipHackathon> sponsorshipHackathons;
}
