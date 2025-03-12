package com.hacof.identity.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.identity.constants.Status;

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
@Table(name = "Hackathons")
public class Hackathon extends AuditBase {
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

    @Column(name = "start_date", columnDefinition = "datetime(6)", nullable = false)
    LocalDateTime startDate; // example:  2024-02-16 12:34:56.123456. -> datetime(6)

    @Column(name = "end_date", columnDefinition = "datetime(6)", nullable = false)
    LocalDateTime endDate;

    @Column(name = "max_teams")
    int maxTeams;

    @ColumnDefault("1")
    @Column(name = "min_team_size")
    int minTeamSize;

    @ColumnDefault("10")
    @Column(name = "max_team_size")
    int maxTeamSize;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "organizer_id", nullable = false)
    User organizer;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status = Status.UPCOMING;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CompetitionRound> rounds;
}
