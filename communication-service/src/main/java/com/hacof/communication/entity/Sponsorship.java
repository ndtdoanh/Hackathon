package com.hacof.communication.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;

import com.hacof.communication.constant.SponsorshipStatus;

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

    @Column(name = "name")
    String name;

    @Column(name = "brand")
    String brand;

    @Lob
    @Column(name = "content")
    String content;

    @Column(name = "money")
    double money;

    @Column(name = "time_from")
    LocalDateTime timeFrom;

    @Column(name = "time_to")
    LocalDateTime timeTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    SponsorshipStatus status;

    @OneToMany(mappedBy = "sponsorship", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<SponsorshipHackathon> sponsorshipHackathons;
}
