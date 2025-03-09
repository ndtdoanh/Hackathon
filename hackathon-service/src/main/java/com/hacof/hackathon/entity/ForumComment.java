package com.hacof.hackathon.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ForumComments")
public class ForumComment extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private String status;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private ForumThread thread;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private ForumComment parentComment;
}
