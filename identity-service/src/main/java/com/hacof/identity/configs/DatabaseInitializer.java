package com.hacof.identity.configs;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hacof.identity.entities.Permission;
import com.hacof.identity.entities.Role;
import com.hacof.identity.entities.User;
import com.hacof.identity.enums.Status;
import com.hacof.identity.exceptions.AppException;
import com.hacof.identity.exceptions.ErrorCode;
import com.hacof.identity.repositories.PermissionRepository;
import com.hacof.identity.repositories.RoleRepository;
import com.hacof.identity.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseInitializer implements CommandLineRunner {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    private static final Map<String, Set<String>> ROLE_PERMISSIONS = Map.of(
            "ADMIN",
                    Set.of(
                            "CREATE_USER",
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "UPDATE_MY_INFO",
                            "DELETE_USER",
                            "ADD_EMAIL",
                            "VERIFY_EMAIL",
                            "CREATE_ROLE",
                            "GET_ROLES",
                            "GET_ROLE",
                            "GET_ROLE_FROM_TOKEN",
                            "UPDATE_ROLE",
                            "DELETE_ROLE",
                            "CREATE_PERMISSION",
                            "GET_PERMISSIONS",
                            "GET_PERMISSION",
                            "UPDATE_PERMISSION",
                            "DELETE_PERMISSION",
                            "DELETE_PERMISSION_FROM_ROLE",
                            "CREATE_PROFILE",
                            "UPDATE_PROFILE",
                            "GET_PROFILES",
                            "GET_PROFILE",
                            "DELETE_PROFILE",
                            "UPLOAD_AVATAR",
                            "GET_FORUMTHREADS"),
            "ORGANIZATION",
                    Set.of(
                            "CREATE_USER",
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "ADD_EMAIL",
                            "VERIFY_EMAIL",
                            "GET_ROLES",
                            "GET_ROLE",
                            "GET_ROLE_FROM_TOKEN",
                            "GET_PERMISSIONS",
                            "GET_PERMISSION",
                            "CREATE_PROFILE",
                            "UPDATE_PROFILE",
                            "GET_PROFILES",
                            "GET_PROFILE",
                            "DELETE_PROFILE",
                            "UPLOAD_AVATAR"),
            "JUDGE",
                    Set.of(
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "ADD_EMAIL",
                            "VERIFY_EMAIL",
                            "GET_ROLES",
                            "GET_ROLE",
                            "GET_ROLE_FROM_TOKEN",
                            "GET_PERMISSIONS",
                            "GET_PERMISSION",
                            "CREATE_PROFILE",
                            "UPDATE_PROFILE",
                            "GET_PROFILES",
                            "GET_PROFILE",
                            "DELETE_PROFILE",
                            "UPLOAD_AVATAR"),
            "MENTOR",
                    Set.of(
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "ADD_EMAIL",
                            "VERIFY_EMAIL",
                            "GET_ROLES",
                            "GET_ROLE",
                            "GET_ROLE_FROM_TOKEN",
                            "GET_PERMISSIONS",
                            "GET_PERMISSION",
                            "CREATE_PROFILE",
                            "UPDATE_PROFILE",
                            "GET_PROFILES",
                            "GET_PROFILE",
                            "DELETE_PROFILE",
                            "UPLOAD_AVATAR"),
            "GUEST", Set.of(),
            "TEAM_MEMBER",
                    Set.of(
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "ADD_EMAIL",
                            "VERIFY_EMAIL",
                            "GET_ROLES",
                            "GET_ROLE",
                            "GET_ROLE_FROM_TOKEN",
                            "GET_PERMISSIONS",
                            "GET_PERMISSION",
                            "CREATE_PROFILE",
                            "UPDATE_PROFILE",
                            "GET_PROFILES",
                            "GET_PROFILE",
                            "DELETE_PROFILE",
                            "UPLOAD_AVATAR"),
            "TEAM_LEADER",
                    Set.of(
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "ADD_EMAIL",
                            "VERIFY_EMAIL",
                            "GET_ROLES",
                            "GET_ROLE",
                            "GET_ROLE_FROM_TOKEN",
                            "GET_PERMISSIONS",
                            "GET_PERMISSION",
                            "CREATE_PROFILE",
                            "UPDATE_PROFILE",
                            "GET_PROFILES",
                            "GET_PROFILE",
                            "DELETE_PROFILE",
                            "UPLOAD_AVATAR"));

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> START INIT DATABASE");

        long countUsers = userRepository.count();
        long countRoles = roleRepository.count();
        long countPermissions = permissionRepository.count();

        if (countPermissions == 0) {
            createPermissions();
        }

        if (countRoles == 0) {
            createRoles();
        } else {
            log.info(">>> SKIP INIT ROLES ~ ALREADY HAVE ROLES...");
        }

        if (countUsers == 0) {
            createDefaultUsers();
        } else {
            log.info(">>> SKIP INIT DATABASE ~ ALREADY HAVE USERS...");
        }
    }

    private void createPermissions() {
        List<Permission> permissions = List.of(
                new Permission("TOKEN", "/api/v1/auth/token", "POST", "AUTH"),
                new Permission("INTROSPECT_TOKEN", "/api/v1/auth/introspect", "POST", "AUTH"),
                new Permission("REFRESH_TOKEN", "/api/v1/auth/refresh", "POST", "AUTH"),
                new Permission("LOGOUT", "/api/v1/auth/logout", "POST", "AUTH"),
                new Permission("OUTBOUND_AUTHENTICATION", "/api/v1/auth/outbound/authentication", "POST", "AUTH"),
                new Permission("CREATE_USER", "/api/v1/users", "POST", "USERS"),
                new Permission("CREATE_PASSWORD", "/api/v1/users/create-password", "POST", "USERS"),
                new Permission("GET_USERS", "/api/v1/users", "GET", "USERS"),
                new Permission("GET_USER", "/api/v1/users/{Id}", "GET", "USERS"),
                new Permission("GET_MY_INFO", "/api/v1/users/my-info", "GET", "USERS"),
                new Permission("UPDATE_MY_INFO", "/api/v1/users/my-info", "PUT", "USERS"),
                new Permission("DELETE_USER", "/api/v1/users/{Id}", "DELETE", "USERS"),
                new Permission("ADD_EMAIL", "/api/v1/users/add-email", "POST", "USERS"),
                new Permission("VERIFY_EMAIL", "/api/v1/users/verify-email", "POST", "USERS"),
                new Permission("CREATE_ROLE", "/api/v1/roles", "POST", "ROLES"),
                new Permission("GET_ROLES", "/api/v1/roles", "GET", "ROLES"),
                new Permission("GET_ROLE", "/api/v1/roles/{Id}", "GET", "ROLES"),
                new Permission("GET_ROLE_FROM_TOKEN", "/api/v1/roles/role-from-token", "GET", "ROLES"),
                new Permission("UPDATE_ROLE", "/api/v1/roles/{Id}", "PUT", "ROLES"),
                new Permission("DELETE_ROLE", "/api/v1/roles/{Id}", "DELETE", "ROLES"),
                new Permission("CREATE_PERMISSION", "/api/v1/permissions", "POST", "PERMISSIONS"),
                new Permission("GET_PERMISSIONS", "/api/v1/permissions", "GET", "PERMISSIONS"),
                new Permission("GET_PERMISSION", "/api/v1/permissions/{Id}", "GET", "PERMISSIONS"),
                new Permission("UPDATE_PERMISSION", "/api/v1/permissions/{Id}", "PUT", "PERMISSIONS"),
                new Permission("DELETE_PERMISSION", "/api/v1/permissions/{Id}", "DELETE", "PERMISSIONS"),
                new Permission(
                        "DELETE_PERMISSION_FROM_ROLE",
                        "/api/v1/permissions/{roleId}/permissions/{permissionId}",
                        "DELETE",
                        "PERMISSIONS"),
                new Permission("CREATE_PROFILE", "/api/v1/profiles", "POST", "PROFILES"),
                new Permission("UPDATE_PROFILE", "/api/v1/profiles/{Id}", "PUT", "PROFILES"),
                new Permission("GET_PROFILES", "/api/v1/profiles", "GET", "PROFILES"),
                new Permission("GET_PROFILE", "/api/v1/profiles/{Id}", "GET", "PROFILES"),
                new Permission("DELETE_PROFILE", "/api/v1/profiles/{Id}", "DELETE", "PROFILES"),
                new Permission("UPLOAD_AVATAR", "/api/v1/profiles/upload-avatar", "POST", "PROFILES"),
                new Permission("GET_FORUMTHREADS", "/api/v1/forumthreads", "GET", "FORUM"));

        permissionRepository.saveAll(permissions);
        log.info(">>> PERMISSIONS CREATED SUCCESSFULLY");
    }

    private void createRoles() {
        for (String roleName : ROLE_PERMISSIONS.keySet()) {
            createRole(roleName, roleName + " role");
        }
        log.info(">>> ROLES CREATED SUCCESSFULLY");
    }

    private void createRole(String roleName, String description) {
        Role role = new Role();
        role.setName(roleName);
        role.setDescription(description);

        Set<String> permissionNames = ROLE_PERMISSIONS.getOrDefault(roleName, Set.of());
        List<Permission> permissions = permissionRepository.findByNameIn(permissionNames);
        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);
    }

    private void createDefaultUsers() {
        Role adminRole = getRole("ADMIN");
        Role organizationRole = getRole("ORGANIZATION");
        Role mentorRole = getRole("MENTOR");
        Role judgeRole = getRole("JUDGE");

        createUser("admin", "12345", "Admin", "System", adminRole);
        createUser("organization", "12345", "Organization", "System", organizationRole);
        createUser("mentor", "12345", "Mentor", "System", mentorRole);
        createUser("judge", "12345", "Judge", "System", judgeRole);

        log.info(">>> DEFAULT USERS CREATED SUCCESSFULLY");
    }

    private void createUser(String email, String password, String firstName, String lastName, Role role) {
        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsVerified(false);
        user.setStatus(Status.ACTIVE);
        user.setRoles(Set.of(role));

        userRepository.save(user);
    }

    private Role getRole(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }
}
