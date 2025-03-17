package com.hacof.communication.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import com.hacof.communication.constant.ConversationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "conversations")
public class Conversation extends AuditCreatedBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    Team team;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    ConversationType type;

    @Column(name = "name")
    String name;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ConversationUser> conversationUsers;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> messages;
}
