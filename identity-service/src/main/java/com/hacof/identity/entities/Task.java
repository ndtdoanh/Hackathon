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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hacof.identity.enums.Priority;
import com.hacof.identity.enums.Status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Column(name = "name", nullable = false)
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "assigned_to", nullable = false)
    User assignedTo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mentor_id", nullable = false)
    Mentor mentor;

    @Column(name = "list_name")
    String listName;

    @Column(name = "board_name")
    String boardName;
}
