package com.hacof.submission.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "feedback_details")
public class FeedbackDetail extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "feedback_id", nullable = false)
    Feedback feedback;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    String content;

    @NotNull
    @Column(name = "max_rating", nullable = false)
    int maxRating;

    @NotNull
    @Min(0)
    @Max(10)
    @Column(name = "rate", nullable = false)
    int rate;

    @Lob
    @Column(name = "note")
    String note;
}
