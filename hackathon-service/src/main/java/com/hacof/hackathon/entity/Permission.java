package com.hacof.hackathon.entity;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "permissions")
public class Permission extends AuditUserBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "permission_name", unique = true)
    String name;

    String apiPath;
    String method;
    String module;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<RolePermission> rolePermissions = new HashSet<>();

    public Permission(String name, String apiPath, String method, String module) {
        this.name = name;
        this.apiPath = apiPath;
        this.method = method;
        this.module = module;
    }
}
