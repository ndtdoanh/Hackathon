package com.hacof.hackathon.entity;

import jakarta.persistence.*;

import com.hacof.hackathon.constant.RoundCampusType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "round_campuses")
public class RoundCampus extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    Round round;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id")
    Campus campus;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    RoundCampusType type;
}
