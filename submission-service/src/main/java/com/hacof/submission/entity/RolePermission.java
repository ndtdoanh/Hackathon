package com.hacof.submission.entity;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "role_permissions")
public class RolePermission extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    Permission permission;
}
