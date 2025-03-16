package com.hacof.submission.entity;

import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id", nullable = false)
    Hackathon hackathon;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sponsorship_id", nullable = false)
    Sponsorship sponsorship;

    @NotNull
    @Column(name = "total_money", nullable = false)
    double totalMoney;

    @OneToMany(mappedBy = "sponsorshipHackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<SponsorshipHackathonDetail> sponsorshipHackathonDetails;
}
