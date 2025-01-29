package com.hacof.identity.configs;

import java.util.ArrayList;
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

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> START INIT DATABASE");

        long countUsers = userRepository.count();
        long countRoles = roleRepository.count();
        long countPermissions = this.permissionRepository.count();

        if (countPermissions == 0) {
            ArrayList<Permission> arr = new ArrayList<>();

            arr.add(new Permission("LOGIN", "/api/v1/auth/token", "POST", "AUTH"));
            arr.add(new Permission("INTROSPECT_TOKEN", "/api/v1/auth/introspect", "POST", "AUTH"));
            arr.add(new Permission("REFRESH_TOKEN", "/api/v1/auth/refresh", "POST", "AUTH"));
            arr.add(new Permission("LOGOUT", "/api/v1/auth/logout", "POST", "AUTH"));

            arr.add(new Permission("CREATE_USER", "/api/v1/users", "POST", "USERS"));
            arr.add(new Permission("GET_USERS", "/api/v1/users", "GET", "USERS"));
            arr.add(new Permission("GET_USER", "/api/v1/users/{Id}", "GET", "USERS"));
            arr.add(new Permission("UPDATE_USER", "/api/v1/users/{Id}", "PUT", "USERS"));
            arr.add(new Permission("DELETE_USER", "/api/v1/users/{Id}", "DELETE", "USERS"));

            arr.add(new Permission("CREATE_ROLE", "/api/v1/roles", "POST", "ROLES"));
            arr.add(new Permission("GET_ROLES", "/api/v1/roles", "GET", "ROLES"));
            arr.add(new Permission("GET_ROLE", "/api/v1/roles/{Id}", "GET", "ROLES"));
            arr.add(new Permission("UPDATE_ROLE", "/api/v1/roles/{Id}", "PUT", "ROLES"));
            arr.add(new Permission("DELETE_ROLE", "/api/v1/roles/{Id}", "DELETE", "ROLES"));

            arr.add(new Permission("CREATE_PERMISSION", "/api/v1/permissions", "POST", "PERMISSIONS"));
            arr.add(new Permission("GET_PERMISSIONS", "/api/v1/permissions", "GET", "PERMISSIONS"));
            arr.add(new Permission("GET_PERMISSION", "/api/v1/permissions/{Id}", "GET", "PERMISSIONS"));
            arr.add(new Permission("UPDATE_PERMISSION", "/api/v1/permissions/{Id}", "PUT", "PERMISSIONS"));
            arr.add(new Permission("DELETE_PERMISSION", "/api/v1/permissions/{Id}", "DELETE", "PERMISSIONS"));

            this.permissionRepository.saveAll(arr);
        }

        if (countRoles == 0) {
            createRole("ADMIN", "Admin role");
            createRole("ORGANIZATION", "Organization role");
            createRole("JUDGE", "Judge role");
            createRole("MENTOR", "Mentor role");
            createRole("GUEST", "Guest role");
            createRole("TEAM_MEMBER", "Team member role");
            createRole("TEAM_LEADER", "Team leader role");

            System.out.println("Roles created successfully!");
        } else {
            System.out.println(">>> SKIP INIT ROLES ~ ALREADY HAVE ROLES...");
        }

        if (countUsers == 0) {
            Role adminRole =
                    roleRepository.findByName("ADMIN").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            Role organizationRole = roleRepository
                    .findByName("ORGANIZATION")
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            Role mentorRole =
                    roleRepository.findByName("MENTOR").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
            Role judgeRole =
                    roleRepository.findByName("JUDGE").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

            createUser("admin@gmail.com", "12345", "Admin", "System", adminRole);
            createUser("organization@gmail.com", "12345", "Organization", "System", organizationRole);
            createUser("mentor@gmail.com", "12345", "Mentor", "System", mentorRole);
            createUser("judge@gmail.com", "12345", "Judge", "System", judgeRole);

            log.info("Users created successfully!");
        } else {
            log.info(">>> SKIP INIT DATABASE ~ ALREADY HAVE USERS...");
        }
    }

    private void createUser(String email, String password, String firstName, String lastName, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsVerified(false);
        user.setStatus(Status.ACTIVE);
        user.setRoles(Set.of(role));

        userRepository.save(user);
    }

    private void createRole(String roleName, String description) {
        Role role = new Role();
        role.setName(roleName);
        role.setDescription(description);

        roleRepository.save(role);
    }
}
