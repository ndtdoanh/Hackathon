package com.hacof.identity.entities;

import java.time.LocalDate;

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

import com.hacof.identity.enums.Status;

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
@Table(name = "hackathons")
public class Hackathon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "banner_image_url")
    String bannerImageUrl;

    @Lob
    @Column(name = "description")
    String description;

    @NotNull
    @Column(name = "start_date", nullable = false)
    LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    LocalDate endDate;

    @Column(name = "max_teams")
    Integer maxTeams;

    @ColumnDefault("1")
    @Column(name = "min_team_size")
    Integer minTeamSize;

    @ColumnDefault("10")
    @Column(name = "max_team_size")
    Integer maxTeamSize;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organizer_id", nullable = false)
    User organizer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status = Status.UPCOMING;
}
