package com.hacof.communication.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.communication.constant.SponsorshipDetailStatus;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sponsorship_hackathon_id")
    SponsorshipHackathon sponsorshipHackathon;

    @Column(name = "money_spent")
    double moneySpent;

    @Lob
    @Column(name = "content")
    String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    SponsorshipDetailStatus status;

    @Column(name = "time_from")
    LocalDateTime timeFrom;

    @Column(name = "time_to")
    LocalDateTime timeTo;

    @OneToMany(mappedBy = "sponsorshipHackathonDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FileUrl> fileUrls = new ArrayList<>();
}
