package com.hacof.identity.entity;

import com.hacof.identity.constant.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mentorship_session_requests")
public class MentorshipSessionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id", nullable = false)
    Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "mentor_id", nullable = false)
    User mentor;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    Team team;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    User createdBy;

    LocalDateTime startTime;
    LocalDateTime endTime;
    String location;
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status = Status.PENDING; //status enum("pending", "rejected", "approved", "deleted", "completed")

    @ManyToOne
    @JoinColumn(name = "evaluated_by")
    User evaluatedBy;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime evaluatedAt;


}
