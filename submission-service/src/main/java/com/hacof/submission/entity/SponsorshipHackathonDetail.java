package com.hacof.submission.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.submission.constant.SponsorshipDetailStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "sponsorship_hackathon_details")
public class SponsorshipHackathonDetail extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sponsorship_hackathon_id", nullable = false)
    SponsorshipHackathon sponsorshipHackathon;

    @NotNull
    @Column(name = "money_spent", nullable = false)
    double moneySpent;

    @Lob
    @Column(name = "content")
    String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    SponsorshipDetailStatus status;

    @NotNull
    @Column(name = "time_from", nullable = false)
    LocalDateTime timeFrom;

    @NotNull
    @Column(name = "time_to", nullable = false)
    LocalDateTime timeTo;
}
