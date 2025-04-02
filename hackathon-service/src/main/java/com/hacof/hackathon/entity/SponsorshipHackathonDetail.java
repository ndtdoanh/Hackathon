package com.hacof.hackathon.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.hackathon.constant.SponsorshipDetailStatus;

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
@Table(name = "sponsorship_hackathon_details")
public class SponsorshipHackathonDetail extends AuditUserBase {

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

    @OneToMany(
            mappedBy = "sponsorshipHackathonDetail",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    List<FileUrl> fileUrls = new ArrayList<>();
}
