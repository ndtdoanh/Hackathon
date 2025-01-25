package com.hacof.identity.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.identity.enums.Name;

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
@Table(name = "competitionrounds")
public class Competitionround {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    Name name;

    @Lob
    @Column(name = "description")
    String description;

    @NotNull
    @Column(name = "start_date", nullable = false)
    Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    Instant endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "hackathon_id", nullable = false)
    Hackathon hackathon;

    @NotNull
    @Column(name = "max_team", nullable = false)
    Integer maxTeam;

    @ColumnDefault("0")
    @Column(name = "is_video_round")
    Boolean isVideoRound;
}
