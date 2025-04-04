package com.hacof.identity.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hacof.identity.constant.Status;
import com.hacof.identity.entity.Hackathon;
import com.hacof.identity.entity.Permission;
import com.hacof.identity.entity.Role;
import com.hacof.identity.entity.RolePermission;
import com.hacof.identity.entity.User;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
import com.hacof.identity.repository.HackathonRepository;
import com.hacof.identity.repository.PermissionRepository;
import com.hacof.identity.repository.RoleRepository;
import com.hacof.identity.repository.UserRepository;

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
    HackathonRepository hackathonRepository;

    private static final Map<String, Set<String>> ROLE_PERMISSIONS = Map.of(
            "ADMIN",
                    Set.of(
                            "GET_LOGS",
                            "GET_LOG",
                            "SEARCH_LOGS",

                            "CREATE_DEVICE",
                            "UPDATE_DEVICE",
                            "DELETE_DEVICE",

                            "CREATE_PERMISSION",
                            "UPDATE_PERMISSION",
                            "DELETE_PERMISSION",
                            "DELETE_PERMISSION_FROM_ROLE",

                            "CREATE_ROLE",
                            "UPDATE_ROLE",
                            "DELETE_ROLE",

                            "DELETE_USER",

                            "CREATE_USER_DEVICE",
                            "UPDATE_USER_DEVICE",
                            "DELETE_USER_DEVICE",

                            "CREATE_USER_DEVICE_TRACK",
                            "UPDATE_USER_DEVICE_TRACK",
                            "DELETE_USER_DEVICE_TRACK",

                            "CREATE_USER_HACKATHON",
                            "UPDATE_USER_HACKATHON",
                            "DELETE_USER_HACKATHON"),
            "ORGANIZATION",
                    Set.of(
                            "CREATE_USER_HACKATHON",
                            "UPDATE_USER_HACKATHON",
                            "DELETE_USER_HACKATHON",
                            "UPDATE_JUDGE_MENTOR_BY_ORGANIZATION"),
            "JUDGE",
                    Set.of(),
            "MENTOR",
                    Set.of(),
            "GUEST",
                    Set.of(),
            "TEAM_MEMBER",
                    Set.of(),
            "TEAM_LEADER",
                    Set.of());

    @Override
    public void run(String... args) throws Exception {
        log.info(">>> START INIT DATABASE");

        long countUsers = userRepository.count();
        long countRoles = roleRepository.count();
        long countPermissions = permissionRepository.count();
        long countHackathons = hackathonRepository.count();

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

        if (countHackathons == 0) {
            createHackathons();
        } else {
            log.info(">>> SKIP INIT HACKATHONS ~ ALREADY HAVE HACKATHONS...");
        }
    }

    private void createPermissions() {
        List<Permission> permissions = List.of(

                new Permission("GET_LOGS", "/api/v1/logs", "GET", "ACTIVITY_LOGS"),
                new Permission("GET_LOG", "/api/v1/logs/{Id}", "GET", "ACTIVITY_LOGS"),
                new Permission("SEARCH_LOGS", "/api/v1/logs/search", "GET", "ACTIVITY_LOGS"),

                new Permission("CREATE_DEVICE", "/api/v1/devices", "POST", "DEVICES"),
                new Permission("UPDATE_DEVICE", "/api/v1/devices/{Id}", "PUT", "DEVICES"),
                new Permission("DELETE_DEVICE", "/api/v1/devices/{Id}", "DELETE", "DEVICES"),

                new Permission("CREATE_PERMISSION", "/api/v1/permissions", "POST", "PERMISSIONS"),
                new Permission("UPDATE_PERMISSION", "/api/v1/permissions/{Id}", "PUT", "PERMISSIONS"),
                new Permission("DELETE_PERMISSION", "/api/v1/permissions/{Id}", "DELETE", "PERMISSIONS"),
                new Permission("DELETE_PERMISSION_FROM_ROLE", "/api/v1/permissions/{roleId}/permissions/{permissionId}", "DELETE", "PERMISSIONS"),

                new Permission("CREATE_ROLE", "/api/v1/roles", "POST", "ROLES"),
                new Permission("UPDATE_ROLE", "/api/v1/roles/{Id}", "PUT", "ROLES"),
                new Permission("DELETE_ROLE", "/api/v1/roles/{Id}", "DELETE", "ROLES"),

                new Permission("UPDATE_JUDGE_MENTOR_BY_ORGANIZATION", "/api/v1/users/organization", "PUT", "USERS"),
                new Permission("DELETE_USER", "/api/v1/users/{Id}", "DELETE", "USERS"),

                new Permission("CREATE_USER_DEVICE", "/api/v1/user-devices", "POST", "USER_DEVICES"),
                new Permission("UPDATE_USER_DEVICE", "/api/v1/user-devices/{Id}", "PUT", "USER_DEVICES"),
                new Permission("DELETE_USER_DEVICE", "/api/v1/user-devices/{Id}", "DELETE", "USER_DEVICES"),

                new Permission("CREATE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks", "POST", "USER_DEVICE_TRACKS"),
                new Permission("UPDATE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks/{Id}", "PUT", "USER_DEVICE_TRACKS"),
                new Permission("DELETE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks/{Id}", "DELETE", "USER_DEVICE_TRACKS"),

                new Permission("CREATE_USER_HACKATHON", "/api/v1/user-hackathons", "POST", "USER_HACKATHONS"),
                new Permission("UPDATE_USER_HACKATHON", "/api/v1/user-hackathons/{Id}", "PUT", "USER_HACKATHONS"),
                new Permission("DELETE_USER_HACKATHON", "/api/v1/user-hackathons/{Id}", "DELETE", "USER_HACKATHONS")
);

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

        for (Permission permission : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRole(role);
            rolePermission.setPermission(permission);
            role.getRolePermissions().add(rolePermission);
        }

        roleRepository.save(role);
    }

    private void createDefaultUsers() {
        Role adminRole = getRole("ADMIN");
        Role organizationRole = getRole("ORGANIZATION");
        Role mentorRole = getRole("MENTOR");
        Role judgeRole = getRole("JUDGE");
        Role teammemberRole = getRole("TEAM_MEMBER");

        createUser("admin", "12345", "Admin", "System", adminRole);
        createUser("organization", "12345", "Organization", "System", organizationRole);
        createUser("mentor", "12345", "Mentor", "System", mentorRole);
        createUser("judge", "12345", "Judge", "System", judgeRole);
        createUser("teammember", "12345", "Team", "Member", teammemberRole);

        log.info(">>> DEFAULT USERS CREATED SUCCESSFULLY");
    }

    private void createUser(String username, String password, String firstName, String lastName, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setVerified(false);
        user.setStatus(Status.ACTIVE);
        user.addRole(role);

        userRepository.save(user);
    }

    private Role getRole(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }

    private void createHackathons() {
        List<Hackathon> hackathons = List.of(
                Hackathon.builder()
                        .title("AI Innovation Challenge")
                        .subTitle("Exploring the Future of AI")
                        .bannerImageUrl("https://example.com/banner1.jpg")
                        .description("A hackathon focused on AI and Machine Learning.")
                        .information("Participants will develop AI-powered solutions.")
                        .startDate(LocalDateTime.of(2025, 6, 10, 9, 0))
                        .endDate(LocalDateTime.of(2025, 6, 12, 18, 0))
                        .maxTeams(20)
                        .minTeamSize(3)
                        .maxTeamSize(5)
                        .contact("contact@ai-hackathon.com")
                        .category("Technology")
                        .status(Status.ACTIVE)
                        .build(),
                Hackathon.builder()
                        .title("Blockchain Revolution")
                        .subTitle("Building the Next-Gen Blockchain Apps")
                        .bannerImageUrl("https://example.com/banner2.jpg")
                        .description("A hackathon focused on decentralized applications and smart contracts.")
                        .information("Teams will create blockchain-based solutions.")
                        .startDate(LocalDateTime.of(2025, 7, 15, 10, 0))
                        .endDate(LocalDateTime.of(2025, 7, 17, 19, 0))
                        .maxTeams(15)
                        .minTeamSize(2)
                        .maxTeamSize(6)
                        .contact("contact@blockchain-hack.com")
                        .category("Finance & Technology")
                        .status(Status.ACTIVE)
                        .build(),
                Hackathon.builder()
                        .title("HealthTech Hackathon")
                        .subTitle("Innovating Healthcare Solutions")
                        .bannerImageUrl("https://example.com/banner3.jpg")
                        .description("A hackathon to develop digital health solutions.")
                        .information("Participants will design and prototype healthcare applications.")
                        .startDate(LocalDateTime.of(2025, 8, 20, 8, 30))
                        .endDate(LocalDateTime.of(2025, 8, 22, 17, 30))
                        .maxTeams(25)
                        .minTeamSize(3)
                        .maxTeamSize(6)
                        .contact("contact@healthtech-hack.com")
                        .category("Healthcare")
                        .status(Status.ACTIVE)
                        .build(),
                Hackathon.builder()
                        .title("Cybersecurity Challenge")
                        .subTitle("Defending the Digital World")
                        .bannerImageUrl("https://example.com/banner4.jpg")
                        .description("A hackathon for ethical hackers and security researchers.")
                        .information("Teams will work on securing digital assets.")
                        .startDate(LocalDateTime.of(2025, 9, 5, 9, 0))
                        .endDate(LocalDateTime.of(2025, 9, 7, 18, 0))
                        .maxTeams(18)
                        .minTeamSize(2)
                        .maxTeamSize(5)
                        .contact("contact@cybersecurity-hack.com")
                        .category("Security")
                        .status(Status.ACTIVE)
                        .build(),
                Hackathon.builder()
                        .title("GreenTech Innovation")
                        .subTitle("Sustainable Solutions for a Greener Future")
                        .bannerImageUrl("https://example.com/banner5.jpg")
                        .description("A hackathon to promote eco-friendly and sustainable technologies.")
                        .information("Participants will create green-tech solutions to tackle climate change.")
                        .startDate(LocalDateTime.of(2025, 10, 12, 10, 0))
                        .endDate(LocalDateTime.of(2025, 10, 14, 16, 0))
                        .maxTeams(30)
                        .minTeamSize(3)
                        .maxTeamSize(7)
                        .contact("contact@greentech-hack.com")
                        .category("Environment")
                        .status(Status.ACTIVE)
                        .build());

        hackathonRepository.saveAll(hackathons);
        log.info(">>> HACKATHONS CREATED SUCCESSFULLY");
    }
}
