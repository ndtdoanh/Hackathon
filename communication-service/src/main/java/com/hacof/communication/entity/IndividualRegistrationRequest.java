package com.hacof.communication.entity;

import jakarta.persistence.*;

import com.hacof.communication.constant.Status;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "individual_registration_requests")
public class IndividualRegistrationRequest extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    Hackathon hackathon;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    User reviewedBy;
}
