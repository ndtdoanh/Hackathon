package com.hacof.identity.entities;

import java.time.LocalDate;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.identity.enums.Priority;
import com.hacof.identity.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Lob
    @Column(name = "document_url")
    private String documentUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.TODO;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority = Priority.MEDIUM;

    @Column(name = "deadline")
    private LocalDate deadline;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignedTo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mentor_id", nullable = false)
    private Mentor mentor;

    @Size(max = 255)
    @Column(name = "list_name")
    private String listName;

    @Size(max = 255)
    @Column(name = "board_name")
    private String boardName;
}
