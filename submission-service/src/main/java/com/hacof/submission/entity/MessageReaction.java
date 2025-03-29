package com.hacof.submission.entity;

import jakarta.persistence.*;

import com.hacof.submission.constant.ReactionType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "message_reactions")
public class MessageReaction extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    Message message;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction")
    ReactionType reactionType;
}
