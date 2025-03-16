package com.hacof.submission.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "forum_categories")
public class ForumCategory extends AuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Lob
    @Column(name = "description")
    String description;

    @Column(name = "section")
    String section;

    @OneToMany(mappedBy = "forumCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ForumThread> forumThreads;
}
