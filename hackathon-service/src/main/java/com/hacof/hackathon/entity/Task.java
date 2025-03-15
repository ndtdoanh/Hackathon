package com.hacof.hackathon.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.hackathon.constant.Priority;
import com.hacof.hackathon.constant.Status;

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
@Table(name = "Tasks")
public class Task extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Column(name = "name")
    String name;

    @Lob
    @Column(name = "description")
    String description;

    @Lob
    @Column(name = "comment")
    String comment;

    @Lob
    @Column(name = "document_url")
    String documentUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status = Status.TODO;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    Priority priority = Priority.MEDIUM;

    @Column(name = "deadline")
    LocalDate deadline;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "assigned_to")
    User assignedTo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mentor_id")
    Mentor mentor;

    @Column(name = "list_name")
    String listName;

    @Column(name = "board_name")
    String boardName;
}
