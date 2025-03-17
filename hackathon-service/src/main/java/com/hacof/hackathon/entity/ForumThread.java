package com.hacof.hackathon.entity;

import java.util.List;

import jakarta.persistence.*;

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
@Table(name = "forum_threads")
public class ForumThread extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "title")
    String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "forum_category_id")
    ForumCategory forumCategory;

    @Column(name = "is_locked")
    boolean isLocked = false;

    @Column(name = "is_pinned")
    boolean isPinned = false;

    @OneToMany(mappedBy = "forumThread", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ThreadPost> threadPosts;
}
