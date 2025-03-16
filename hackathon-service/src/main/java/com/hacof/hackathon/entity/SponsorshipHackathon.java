package com.hacof.hackathon.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "sponsorship_hackathons")
public class SponsorshipHackathon extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sponsorship_id")
    Sponsorship sponsorship;

    @Column(name = "total_money")
    double totalMoney;

    @OneToMany(mappedBy = "sponsorshipHackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<SponsorshipHackathonDetail> sponsorshipHackathonDetails;
}
