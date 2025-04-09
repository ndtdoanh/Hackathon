package com.hacof.identity.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hacof.identity.constant.CategoryStatus;
import com.hacof.identity.constant.OrganizationStatus;
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

                            "CREATE_PERMISSION",
                            "UPDATE_PERMISSION",
                            "DELETE_PERMISSION",
                            "DELETE_PERMISSION_FROM_ROLE",

                            "CREATE_ROLE",
                            "UPDATE_ROLE",
                            "DELETE_ROLE",

                            "DELETE_USER",

                            "APPROVE_BLOG_POST",
                            "REJECT_BLOG_POST",
                            "DELETE_BLOG_POST"
                    ),
            "ORGANIZER",
                    Set.of(
                            "CREATE_DEVICE",
                            "UPDATE_DEVICE",
                            "DELETE_DEVICE",

                            "UPDATE_JUDGE_MENTOR_BY_ORGANIZER",

                            "CREATE_USER_DEVICE",
                            "UPDATE_USER_DEVICE",
                            "DELETE_USER_DEVICE",

                            "CREATE_USER_DEVICE_TRACK",
                            "UPDATE_USER_DEVICE_TRACK",
                            "DELETE_USER_DEVICE_TRACK",

                            "CREATE_USER_HACKATHON",
                            "DELETE_USER_HACKATHON",

                            "CREATE_NOTIFICATION",
                            "DELETE_NOTIFICATION",
                            "UPDATE_READ_STATUS",

                            "CREATE_BLOG_POST",
                            "SUBMIT_BLOG_POST",

                            "CREATE_JUDGE_ROUND",
                            "UPDATE_JUDGE_ROUND",
                            "DELETE_JUDGE_ROUND",
                            "UPDATE_JUDGE_ROUND_BY_JUDGE_ID",
                            "DELETE_JUDGE_ROUND_BY_JUDGE_ID_AND_ROUND_ID",

                            "CREATE_ROUND_MARK_CRITERIA",
                            "UPDATE_ROUND_MARK_CRITERIA",
                            "DELETE_ROUND_MARK_CRITERIA",

                            "CREATE_TEAM_ROUND_JUDGE",
                            "UPDATE_TEAM_ROUND_JUDGE",
                            "DELETE_TEAM_ROUND_JUDGE",
                            "DELETE_BY_TEAM_ROUND_AND_JUDGE"
                    ),
            "JUDGE",
                    Set.of(
                            "CREATE_JUDGE_SUBMISSION",
                            "UPDATE_JUDGE_SUBMISSION",
                            "DELETE_JUDGE_SUBMISSION"
                    ),
            "MENTOR",
                    Set.of(),
            "TEAM_MEMBER",
                    Set.of(
                            "CREATE_SUBMISSION",
                            "UPDATE_SUBMISSION",
                            "DELETE_SUBMISSION"
                    ),
            "TEAM_LEADER",
                    Set.of(
                            "CREATE_SUBMISSION",
                            "UPDATE_SUBMISSION",
                            "DELETE_SUBMISSION"
                    )
    );

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

                new Permission("UPDATE_JUDGE_MENTOR_BY_ORGANIZER", "/api/v1/users/organizer", "PUT", "USERS"),
                new Permission("DELETE_USER", "/api/v1/users/{Id}", "DELETE", "USERS"),

                new Permission("CREATE_USER_DEVICE", "/api/v1/user-devices", "POST", "USER_DEVICES"),
                new Permission("UPDATE_USER_DEVICE", "/api/v1/user-devices/{Id}", "PUT", "USER_DEVICES"),
                new Permission("DELETE_USER_DEVICE", "/api/v1/user-devices/{Id}", "DELETE", "USER_DEVICES"),

                new Permission("CREATE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks", "POST", "USER_DEVICE_TRACKS"),
                new Permission("UPDATE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks/{Id}", "PUT", "USER_DEVICE_TRACKS"),
                new Permission("DELETE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks/{Id}", "DELETE", "USER_DEVICE_TRACKS"),

                new Permission("CREATE_USER_HACKATHON", "/api/v1/user-hackathons", "POST", "USER_HACKATHONS"),
                new Permission("DELETE_USER_HACKATHON", "/api/v1/user-hackathons/{Id}", "DELETE", "USER_HACKATHONS"),

                new Permission("CREATE_NOTIFICATION", "/api/v1/notifications", "POST", "NOTIFICATIONS"),
                new Permission("DELETE_NOTIFICATION", "/api/v1/notifications/{Id}", "DELETE", "NOTIFICATIONS"),
                new Permission("UPDATE_READ_STATUS", "/api/v1/notifications/notification-deliveries/read-status", "PUT", "NOTIFICATIONS"),

                new Permission("CREATE_BLOG_POST", "/api/v1/blog-posts", "POST", "BLOG_POSTS"),
                new Permission("SUBMIT_BLOG_POST", "/api/v1/blog-posts/{id}/submit", "PUT", "BLOG_POSTS"),
                new Permission("APPROVE_BLOG_POST", "/api/v1/blog-posts/{id}/approve", "PUT", "BLOG_POSTS"),
                new Permission("REJECT_BLOG_POST", "/api/v1/blog-posts/{id}/reject", "PUT", "BLOG_POSTS"),
                new Permission("DELETE_BLOG_POST", "/api/v1/blog-posts/{id}", "DELETE", "BLOG_POSTS"),

                new Permission("CREATE_JUDGE_ROUND", "/api/v1/judge-rounds", "POST", "JUDGE_ROUNDS"),
                new Permission("UPDATE_JUDGE_ROUND", "/api/v1/judge-rounds/{id}", "PUT", "JUDGE_ROUNDS"),
                new Permission("DELETE_JUDGE_ROUND", "/api/v1/judge-rounds/{id}", "DELETE", "JUDGE_ROUNDS"),
                new Permission("UPDATE_JUDGE_ROUND_BY_JUDGE_ID", "/api/v1/judge-rounds/judge/{judgeId}", "PUT", "JUDGE_ROUNDS"),
                new Permission("DELETE_JUDGE_ROUND_BY_JUDGE_ID_AND_ROUND_ID", "/api/v1/judge-rounds/by-judge-round", "DELETE", "JUDGE_ROUNDS"),

                new Permission("CREATE_ROUND_MARK_CRITERIA", "/api/v1/roundmarkcriteria", "POST", "ROUND_MARK_CRITERIA"),
                new Permission("UPDATE_ROUND_MARK_CRITERIA", "/api/v1/roundmarkcriteria/{id}", "PUT", "ROUND_MARK_CRITERIA"),
                new Permission("DELETE_ROUND_MARK_CRITERIA", "/api/v1/roundmarkcriteria/{id}", "DELETE", "ROUND_MARK_CRITERIA"),

                new Permission("CREATE_TEAM_ROUND_JUDGE", "/api/v1/teamroundjudges", "POST", "TEAM_ROUND_JUDGE"),
                new Permission("UPDATE_TEAM_ROUND_JUDGE", "/api/v1/teamroundjudges/{id}", "PUT", "TEAM_ROUND_JUDGE"),
                new Permission("DELETE_TEAM_ROUND_JUDGE", "/api/v1/teamroundjudges/{id}", "DELETE", "TEAM_ROUND_JUDGE"),
                new Permission("DELETE_BY_TEAM_ROUND_AND_JUDGE", "/api/v1/teamroundjudges/by-team-round-judge", "DELETE", "TEAM_ROUND_JUDGE"),

                new Permission("CREATE_JUDGE_SUBMISSION", "/api/v1/judge-submissions", "POST", "JUDGE_SUBMISSIONS"),
                new Permission("UPDATE_JUDGE_SUBMISSION", "/api/v1/judge-submissions/{id}", "PUT", "JUDGE_SUBMISSIONS"),
                new Permission("DELETE_JUDGE_SUBMISSION", "/api/v1/judge-submissions/{id}", "DELETE", "JUDGE_SUBMISSIONS"),

                new Permission("CREATE_SUBMISSION", "/api/v1/submissions", "POST", "SUBMISSIONS"),
                new Permission("UPDATE_SUBMISSION", "/api/v1/submissions/{id}", "PUT", "SUBMISSIONS"),
                new Permission("DELETE_SUBMISSION", "/api/v1/submissions/{id}", "DELETE", "SUBMISSIONS")
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
        Role organizerRole = getRole("ORGANIZER");
        Role mentorRole = getRole("MENTOR");
        Role judgeRole = getRole("JUDGE");
        Role teammemberRole = getRole("TEAM_MEMBER");

        createUser("admin1", "admin1@gmail.com", "12345", "Admin1", "System", adminRole);
        createUser("admin2", "admin2@gmail.com", "12345", "Admin2", "System", adminRole);
        createUser("admin3", "admin3@gmail.com", "12345", "Admin3", "System", adminRole);
        createUser("admin4", "admin4@gmail.com", "12345", "Admin4", "System", adminRole);
        createUser("admin5", "admin5@gmail.com", "12345", "Admin5", "System", adminRole);

        createUser("organizer1", "organizer1@gmail.com", "12345", "Organizer1", "System", organizerRole);
        createUser("organizer2", "organizer2@gmail.com", "12345", "Organizer2", "System", organizerRole);
        createUser("organizer3", "organizer3@gmail.com", "12345", "Organizer3", "System", organizerRole);
        createUser("organizer4", "organizer4@gmail.com", "12345", "Organizer4", "System", organizerRole);
        createUser("organizer5", "organizer5@gmail.com", "12345", "Organizer5", "System", organizerRole);
        createUser("organizer6", "organizer6@gmail.com", "12345", "Organizer6", "System", organizerRole);
        createUser("organizer7", "organizer7@gmail.com", "12345", "Organizer7", "System", organizerRole);
        createUser("organizer8", "organizer8@gmail.com", "12345", "Organizer8", "System", organizerRole);
        createUser("organizer9", "organizer9@gmail.com", "12345", "Organizer9", "System", organizerRole);
        createUser("organizer10", "organizer10@gmail.com", "12345", "Organizer10", "System", organizerRole);

        createUser("mentor1", "mentor1@gmail.com", "12345", "Mentor1", "System", mentorRole);
        createUser("mentor2", "mentor2@gmail.com", "12345", "Mentor2", "System", mentorRole);
        createUser("mentor3", "mentor3@gmail.com", "12345", "Mentor3", "System", mentorRole);
        createUser("mentor4", "mentor4@gmail.com", "12345", "Mentor4", "System", mentorRole);
        createUser("mentor5", "mentor5@gmail.com", "12345", "Mentor5", "System", mentorRole);
        createUser("mentor6", "mentor6@gmail.com", "12345", "Mentor6", "System", mentorRole);
        createUser("mentor7", "mentor7@gmail.com", "12345", "Mentor7", "System", mentorRole);
        createUser("mentor8", "mentor8@gmail.com", "12345", "Mentor8", "System", mentorRole);
        createUser("mentor9", "mentor9@gmail.com", "12345", "Mentor9", "System", mentorRole);
        createUser("mentor10", "mentor10@gmail.com", "12345", "Mentor10", "System", mentorRole);
        createUser("mentor11", "mentor11@gmail.com", "12345", "Mentor11", "System", mentorRole);
        createUser("mentor12", "mentor12@gmail.com", "12345", "Mentor12", "System", mentorRole);
        createUser("mentor13", "mentor13@gmail.com", "12345", "Mentor13", "System", mentorRole);
        createUser("mentor14", "mentor14@gmail.com", "12345", "Mentor14", "System", mentorRole);
        createUser("mentor15", "mentor15@gmail.com", "12345", "Mentor15", "System", mentorRole);
        createUser("mentor16", "mentor16@gmail.com", "12345", "Mentor16", "System", mentorRole);
        createUser("mentor17", "mentor17@gmail.com", "12345", "Mentor17", "System", mentorRole);
        createUser("mentor18", "mentor18@gmail.com", "12345", "Mentor18", "System", mentorRole);
        createUser("mentor19", "mentor19@gmail.com", "12345", "Mentor19", "System", mentorRole);
        createUser("mentor20", "mentor20@gmail.com", "12345", "Mentor20", "System", mentorRole);

        createUser("judge1", "judge1@gmail.com", "12345", "Judge1", "System", judgeRole);
        createUser("judge2", "judge2@gmail.com", "12345", "Judge2", "System", judgeRole);
        createUser("judge3", "judge3@gmail.com", "12345", "Judge3", "System", judgeRole);
        createUser("judge4", "judge4@gmail.com", "12345", "Judge4", "System", judgeRole);
        createUser("judge5", "judge5@gmail.com", "12345", "Judge5", "System", judgeRole);
        createUser("judge6", "judge6@gmail.com", "12345", "Judge6", "System", judgeRole);
        createUser("judge7", "judge7@gmail.com", "12345", "Judge7", "System", judgeRole);
        createUser("judge8", "judge8@gmail.com", "12345", "Judge8", "System", judgeRole);
        createUser("judge9", "judge9@gmail.com", "12345", "Judge9", "System", judgeRole);
        createUser("judge10", "judge10@gmail.com", "12345", "Judge10", "System", judgeRole);
        createUser("judge11", "judge11@gmail.com", "12345", "Judge11", "System", judgeRole);
        createUser("judge12", "judge12@gmail.com", "12345", "Judge12", "System", judgeRole);
        createUser("judge13", "judge13@gmail.com", "12345", "Judge13", "System", judgeRole);
        createUser("judge14", "judge14@gmail.com", "12345", "Judge14", "System", judgeRole);
        createUser("judge15", "judge15@gmail.com", "12345", "Judge15", "System", judgeRole);
        createUser("judge16", "judge16@gmail.com", "12345", "Judge16", "System", judgeRole);
        createUser("judge17", "judge17@gmail.com", "12345", "Judge17", "System", judgeRole);
        createUser("judge18", "judge18@gmail.com", "12345", "Judge18", "System", judgeRole);
        createUser("judge19", "judge19@gmail.com", "12345", "Judge19", "System", judgeRole);
        createUser("judge20", "judge20@gmail.com", "12345", "Judge20", "System", judgeRole);

        createUser("teammember1", "teammember1@gmail.com", "12345", "Member1", "Team", teammemberRole);
        createUser("teammember2", "teammember2@gmail.com", "12345", "Member2", "Team", teammemberRole);
        createUser("teammember3", "teammember3@gmail.com", "12345", "Member3", "Team", teammemberRole);
        createUser("teammember4", "teammember4@gmail.com", "12345", "Member4", "Team", teammemberRole);
        createUser("teammember5", "teammember5@gmail.com", "12345", "Member5", "Team", teammemberRole);
        createUser("teammember6", "teammember6@gmail.com", "12345", "Member6", "Team", teammemberRole);
        createUser("teammember7", "teammember7@gmail.com", "12345", "Member7", "Team", teammemberRole);
        createUser("teammember8", "teammember8@gmail.com", "12345", "Member8", "Team", teammemberRole);
        createUser("teammember9", "teammember9@gmail.com", "12345", "Member9", "Team", teammemberRole);
        createUser("teammember10", "teammember10@gmail.com", "12345", "Member10", "Team", teammemberRole);
        createUser("teammember11", "teammember11@gmail.com", "12345", "Member11", "Team", teammemberRole);
        createUser("teammember12", "teammember12@gmail.com", "12345", "Member12", "Team", teammemberRole);
        createUser("teammember13", "teammember13@gmail.com", "12345", "Member13", "Team", teammemberRole);
        createUser("teammember14", "teammember14@gmail.com", "12345", "Member14", "Team", teammemberRole);
        createUser("teammember15", "teammember15@gmail.com", "12345", "Member15", "Team", teammemberRole);
        createUser("teammember16", "teammember16@gmail.com", "12345", "Member16", "Team", teammemberRole);
        createUser("teammember17", "teammember17@gmail.com", "12345", "Member17", "Team", teammemberRole);
        createUser("teammember18", "teammember18@gmail.com", "12345", "Member18", "Team", teammemberRole);
        createUser("teammember19", "teammember19@gmail.com", "12345", "Member19", "Team", teammemberRole);
        createUser("teammember20", "teammember20@gmail.com", "12345", "Member20", "Team", teammemberRole);
        createUser("teammember21", "teammember21@gmail.com", "12345", "Member21", "Team", teammemberRole);
        createUser("teammember22", "teammember22@gmail.com", "12345", "Member22", "Team", teammemberRole);
        createUser("teammember23", "teammember23@gmail.com", "12345", "Member23", "Team", teammemberRole);
        createUser("teammember24", "teammember24@gmail.com", "12345", "Member24", "Team", teammemberRole);
        createUser("teammember25", "teammember25@gmail.com", "12345", "Member25", "Team", teammemberRole);
        createUser("teammember26", "teammember26@gmail.com", "12345", "Member26", "Team", teammemberRole);
        createUser("teammember27", "teammember27@gmail.com", "12345", "Member27", "Team", teammemberRole);
        createUser("teammember28", "teammember28@gmail.com", "12345", "Member28", "Team", teammemberRole);
        createUser("teammember29", "teammember29@gmail.com", "12345", "Member29", "Team", teammemberRole);
        createUser("teammember30", "teammember30@gmail.com", "12345", "Member30", "Team", teammemberRole);
        createUser("teammember31", "teammember31@gmail.com", "12345", "Member31", "Team", teammemberRole);
        createUser("teammember32", "teammember32@gmail.com", "12345", "Member32", "Team", teammemberRole);
        createUser("teammember33", "teammember33@gmail.com", "12345", "Member33", "Team", teammemberRole);
        createUser("teammember34", "teammember34@gmail.com", "12345", "Member34", "Team", teammemberRole);
        createUser("teammember35", "teammember35@gmail.com", "12345", "Member35", "Team", teammemberRole);
        createUser("teammember36", "teammember36@gmail.com", "12345", "Member36", "Team", teammemberRole);
        createUser("teammember37", "teammember37@gmail.com", "12345", "Member37", "Team", teammemberRole);
        createUser("teammember38", "teammember38@gmail.com", "12345", "Member38", "Team", teammemberRole);
        createUser("teammember39", "teammember39@gmail.com", "12345", "Member39", "Team", teammemberRole);
        createUser("teammember40", "teammember40@gmail.com", "12345", "Member40", "Team", teammemberRole);
        createUser("teammember41", "teammember41@gmail.com", "12345", "Member41", "Team", teammemberRole);
        createUser("teammember42", "teammember42@gmail.com", "12345", "Member42", "Team", teammemberRole);
        createUser("teammember43", "teammember43@gmail.com", "12345", "Member43", "Team", teammemberRole);
        createUser("teammember44", "teammember44@gmail.com", "12345", "Member44", "Team", teammemberRole);
        createUser("teammember45", "teammember45@gmail.com", "12345", "Member45", "Team", teammemberRole);
        createUser("teammember46", "teammember46@gmail.com", "12345", "Member46", "Team", teammemberRole);
        createUser("teammember47", "teammember47@gmail.com", "12345", "Member47", "Team", teammemberRole);
        createUser("teammember48", "teammember48@gmail.com", "12345", "Member48", "Team", teammemberRole);
        createUser("teammember49", "teammember49@gmail.com", "12345", "Member49", "Team", teammemberRole);
        createUser("teammember50", "teammember50@gmail.com", "12345", "Member50", "Team", teammemberRole);

        createUser("teammember51", "teammember51@gmail.com", "12345", "Member51", "Team", teammemberRole);
        createUser("teammember52", "teammember52@gmail.com", "12345", "Member52", "Team", teammemberRole);
        createUser("teammember53", "teammember53@gmail.com", "12345", "Member53", "Team", teammemberRole);
        createUser("teammember54", "teammember54@gmail.com", "12345", "Member54", "Team", teammemberRole);
        createUser("teammember55", "teammember55@gmail.com", "12345", "Member55", "Team", teammemberRole);
        createUser("teammember56", "teammember56@gmail.com", "12345", "Member56", "Team", teammemberRole);
        createUser("teammember57", "teammember57@gmail.com", "12345", "Member57", "Team", teammemberRole);
        createUser("teammember58", "teammember58@gmail.com", "12345", "Member58", "Team", teammemberRole);
        createUser("teammember59", "teammember59@gmail.com", "12345", "Member59", "Team", teammemberRole);
        createUser("teammember60", "teammember60@gmail.com", "12345", "Member60", "Team", teammemberRole);
        createUser("teammember61", "teammember61@gmail.com", "12345", "Member61", "Team", teammemberRole);
        createUser("teammember62", "teammember62@gmail.com", "12345", "Member62", "Team", teammemberRole);
        createUser("teammember63", "teammember63@gmail.com", "12345", "Member63", "Team", teammemberRole);
        createUser("teammember64", "teammember64@gmail.com", "12345", "Member64", "Team", teammemberRole);
        createUser("teammember65", "teammember65@gmail.com", "12345", "Member65", "Team", teammemberRole);
        createUser("teammember66", "teammember66@gmail.com", "12345", "Member66", "Team", teammemberRole);
        createUser("teammember67", "teammember67@gmail.com", "12345", "Member67", "Team", teammemberRole);
        createUser("teammember68", "teammember68@gmail.com", "12345", "Member68", "Team", teammemberRole);
        createUser("teammember69", "teammember69@gmail.com", "12345", "Member69", "Team", teammemberRole);
        createUser("teammember70", "teammember70@gmail.com", "12345", "Member70", "Team", teammemberRole);
        createUser("teammember71", "teammember71@gmail.com", "12345", "Member71", "Team", teammemberRole);
        createUser("teammember72", "teammember72@gmail.com", "12345", "Member72", "Team", teammemberRole);
        createUser("teammember73", "teammember73@gmail.com", "12345", "Member73", "Team", teammemberRole);
        createUser("teammember74", "teammember74@gmail.com", "12345", "Member74", "Team", teammemberRole);
        createUser("teammember75", "teammember75@gmail.com", "12345", "Member75", "Team", teammemberRole);
        createUser("teammember76", "teammember76@gmail.com", "12345", "Member76", "Team", teammemberRole);
        createUser("teammember77", "teammember77@gmail.com", "12345", "Member77", "Team", teammemberRole);
        createUser("teammember78", "teammember78@gmail.com", "12345", "Member78", "Team", teammemberRole);
        createUser("teammember79", "teammember79@gmail.com", "12345", "Member79", "Team", teammemberRole);
        createUser("teammember80", "teammember80@gmail.com", "12345", "Member80", "Team", teammemberRole);
        createUser("teammember81", "teammember81@gmail.com", "12345", "Member81", "Team", teammemberRole);
        createUser("teammember82", "teammember82@gmail.com", "12345", "Member82", "Team", teammemberRole);
        createUser("teammember83", "teammember83@gmail.com", "12345", "Member83", "Team", teammemberRole);
        createUser("teammember84", "teammember84@gmail.com", "12345", "Member84", "Team", teammemberRole);
        createUser("teammember85", "teammember85@gmail.com", "12345", "Member85", "Team", teammemberRole);
        createUser("teammember86", "teammember86@gmail.com", "12345", "Member86", "Team", teammemberRole);
        createUser("teammember87", "teammember87@gmail.com", "12345", "Member87", "Team", teammemberRole);
        createUser("teammember88", "teammember88@gmail.com", "12345", "Member88", "Team", teammemberRole);
        createUser("teammember89", "teammember89@gmail.com", "12345", "Member89", "Team", teammemberRole);
        createUser("teammember90", "teammember90@gmail.com", "12345", "Member90", "Team", teammemberRole);
        createUser("teammember91", "teammember91@gmail.com", "12345", "Member91", "Team", teammemberRole);
        createUser("teammember92", "teammember92@gmail.com", "12345", "Member92", "Team", teammemberRole);
        createUser("teammember93", "teammember93@gmail.com", "12345", "Member93", "Team", teammemberRole);
        createUser("teammember94", "teammember94@gmail.com", "12345", "Member94", "Team", teammemberRole);
        createUser("teammember95", "teammember95@gmail.com", "12345", "Member95", "Team", teammemberRole);
        createUser("teammember96", "teammember96@gmail.com", "12345", "Member96", "Team", teammemberRole);
        createUser("teammember97", "teammember97@gmail.com", "12345", "Member97", "Team", teammemberRole);
        createUser("teammember98", "teammember98@gmail.com", "12345", "Member98", "Team", teammemberRole);
        createUser("teammember99", "teammember99@gmail.com", "12345", "Member99", "Team", teammemberRole);
        createUser("teammember100", "teammember100@gmail.com", "12345", "Member100", "Team", teammemberRole);

        createUser("teammember101", "teammember101@gmail.com", "12345", "Member101", "Team", teammemberRole);
        createUser("teammember102", "teammember102@gmail.com", "12345", "Member102", "Team", teammemberRole);
        createUser("teammember103", "teammember103@gmail.com", "12345", "Member103", "Team", teammemberRole);
        createUser("teammember104", "teammember104@gmail.com", "12345", "Member104", "Team", teammemberRole);
        createUser("teammember105", "teammember105@gmail.com", "12345", "Member105", "Team", teammemberRole);
        createUser("teammember106", "teammember106@gmail.com", "12345", "Member106", "Team", teammemberRole);
        createUser("teammember107", "teammember107@gmail.com", "12345", "Member107", "Team", teammemberRole);
        createUser("teammember108", "teammember108@gmail.com", "12345", "Member108", "Team", teammemberRole);
        createUser("teammember109", "teammember109@gmail.com", "12345", "Member109", "Team", teammemberRole);
        createUser("teammember110", "teammember110@gmail.com", "12345", "Member110", "Team", teammemberRole);
        createUser("teammember111", "teammember111@gmail.com", "12345", "Member111", "Team", teammemberRole);
        createUser("teammember112", "teammember112@gmail.com", "12345", "Member112", "Team", teammemberRole);
        createUser("teammember113", "teammember113@gmail.com", "12345", "Member113", "Team", teammemberRole);
        createUser("teammember114", "teammember114@gmail.com", "12345", "Member114", "Team", teammemberRole);
        createUser("teammember115", "teammember115@gmail.com", "12345", "Member115", "Team", teammemberRole);
        createUser("teammember116", "teammember116@gmail.com", "12345", "Member116", "Team", teammemberRole);
        createUser("teammember117", "teammember117@gmail.com", "12345", "Member117", "Team", teammemberRole);
        createUser("teammember118", "teammember118@gmail.com", "12345", "Member118", "Team", teammemberRole);
        createUser("teammember119", "teammember119@gmail.com", "12345", "Member119", "Team", teammemberRole);
        createUser("teammember120", "teammember120@gmail.com", "12345", "Member120", "Team", teammemberRole);
        createUser("teammember121", "teammember121@gmail.com", "12345", "Member121", "Team", teammemberRole);
        createUser("teammember122", "teammember122@gmail.com", "12345", "Member122", "Team", teammemberRole);
        createUser("teammember123", "teammember123@gmail.com", "12345", "Member123", "Team", teammemberRole);
        createUser("teammember124", "teammember124@gmail.com", "12345", "Member124", "Team", teammemberRole);
        createUser("teammember125", "teammember125@gmail.com", "12345", "Member125", "Team", teammemberRole);
        createUser("teammember126", "teammember126@gmail.com", "12345", "Member126", "Team", teammemberRole);
        createUser("teammember127", "teammember127@gmail.com", "12345", "Member127", "Team", teammemberRole);
        createUser("teammember128", "teammember128@gmail.com", "12345", "Member128", "Team", teammemberRole);
        createUser("teammember129", "teammember129@gmail.com", "12345", "Member129", "Team", teammemberRole);
        createUser("teammember130", "teammember130@gmail.com", "12345", "Member130", "Team", teammemberRole);
        createUser("teammember131", "teammember131@gmail.com", "12345", "Member131", "Team", teammemberRole);
        createUser("teammember132", "teammember132@gmail.com", "12345", "Member132", "Team", teammemberRole);
        createUser("teammember133", "teammember133@gmail.com", "12345", "Member133", "Team", teammemberRole);
        createUser("teammember134", "teammember134@gmail.com", "12345", "Member134", "Team", teammemberRole);
        createUser("teammember135", "teammember135@gmail.com", "12345", "Member135", "Team", teammemberRole);
        createUser("teammember136", "teammember136@gmail.com", "12345", "Member136", "Team", teammemberRole);
        createUser("teammember137", "teammember137@gmail.com", "12345", "Member137", "Team", teammemberRole);
        createUser("teammember138", "teammember138@gmail.com", "12345", "Member138", "Team", teammemberRole);
        createUser("teammember139", "teammember139@gmail.com", "12345", "Member139", "Team", teammemberRole);
        createUser("teammember140", "teammember140@gmail.com", "12345", "Member140", "Team", teammemberRole);
        createUser("teammember141", "teammember141@gmail.com", "12345", "Member141", "Team", teammemberRole);
        createUser("teammember142", "teammember142@gmail.com", "12345", "Member142", "Team", teammemberRole);
        createUser("teammember143", "teammember143@gmail.com", "12345", "Member143", "Team", teammemberRole);
        createUser("teammember144", "teammember144@gmail.com", "12345", "Member144", "Team", teammemberRole);
        createUser("teammember145", "teammember145@gmail.com", "12345", "Member145", "Team", teammemberRole);
        createUser("teammember146", "teammember146@gmail.com", "12345", "Member146", "Team", teammemberRole);
        createUser("teammember147", "teammember147@gmail.com", "12345", "Member147", "Team", teammemberRole);
        createUser("teammember148", "teammember148@gmail.com", "12345", "Member148", "Team", teammemberRole);
        createUser("teammember149", "teammember149@gmail.com", "12345", "Member149", "Team", teammemberRole);
        createUser("teammember150", "teammember150@gmail.com", "12345", "Member150", "Team", teammemberRole);

        createUser("teammember151", "teammember151@gmail.com", "12345", "Member151", "Team", teammemberRole);
        createUser("teammember152", "teammember152@gmail.com", "12345", "Member152", "Team", teammemberRole);
        createUser("teammember153", "teammember153@gmail.com", "12345", "Member153", "Team", teammemberRole);
        createUser("teammember154", "teammember154@gmail.com", "12345", "Member154", "Team", teammemberRole);
        createUser("teammember155", "teammember155@gmail.com", "12345", "Member155", "Team", teammemberRole);
        createUser("teammember156", "teammember156@gmail.com", "12345", "Member156", "Team", teammemberRole);
        createUser("teammember157", "teammember157@gmail.com", "12345", "Member157", "Team", teammemberRole);
        createUser("teammember158", "teammember158@gmail.com", "12345", "Member158", "Team", teammemberRole);
        createUser("teammember159", "teammember159@gmail.com", "12345", "Member159", "Team", teammemberRole);
        createUser("teammember160", "teammember160@gmail.com", "12345", "Member160", "Team", teammemberRole);
        createUser("teammember161", "teammember161@gmail.com", "12345", "Member161", "Team", teammemberRole);
        createUser("teammember162", "teammember162@gmail.com", "12345", "Member162", "Team", teammemberRole);
        createUser("teammember163", "teammember163@gmail.com", "12345", "Member163", "Team", teammemberRole);
        createUser("teammember164", "teammember164@gmail.com", "12345", "Member164", "Team", teammemberRole);
        createUser("teammember165", "teammember165@gmail.com", "12345", "Member165", "Team", teammemberRole);
        createUser("teammember166", "teammember166@gmail.com", "12345", "Member166", "Team", teammemberRole);
        createUser("teammember167", "teammember167@gmail.com", "12345", "Member167", "Team", teammemberRole);
        createUser("teammember168", "teammember168@gmail.com", "12345", "Member168", "Team", teammemberRole);
        createUser("teammember169", "teammember169@gmail.com", "12345", "Member169", "Team", teammemberRole);
        createUser("teammember170", "teammember170@gmail.com", "12345", "Member170", "Team", teammemberRole);
        createUser("teammember171", "teammember171@gmail.com", "12345", "Member171", "Team", teammemberRole);
        createUser("teammember172", "teammember172@gmail.com", "12345", "Member172", "Team", teammemberRole);
        createUser("teammember173", "teammember173@gmail.com", "12345", "Member173", "Team", teammemberRole);
        createUser("teammember174", "teammember174@gmail.com", "12345", "Member174", "Team", teammemberRole);
        createUser("teammember175", "teammember175@gmail.com", "12345", "Member175", "Team", teammemberRole);
        createUser("teammember176", "teammember176@gmail.com", "12345", "Member176", "Team", teammemberRole);
        createUser("teammember177", "teammember177@gmail.com", "12345", "Member177", "Team", teammemberRole);
        createUser("teammember178", "teammember178@gmail.com", "12345", "Member178", "Team", teammemberRole);
        createUser("teammember179", "teammember179@gmail.com", "12345", "Member179", "Team", teammemberRole);
        createUser("teammember180", "teammember180@gmail.com", "12345", "Member180", "Team", teammemberRole);
        createUser("teammember181", "teammember181@gmail.com", "12345", "Member181", "Team", teammemberRole);
        createUser("teammember182", "teammember182@gmail.com", "12345", "Member182", "Team", teammemberRole);
        createUser("teammember183", "teammember183@gmail.com", "12345", "Member183", "Team", teammemberRole);
        createUser("teammember184", "teammember184@gmail.com", "12345", "Member184", "Team", teammemberRole);
        createUser("teammember185", "teammember185@gmail.com", "12345", "Member185", "Team", teammemberRole);
        createUser("teammember186", "teammember186@gmail.com", "12345", "Member186", "Team", teammemberRole);
        createUser("teammember187", "teammember187@gmail.com", "12345", "Member187", "Team", teammemberRole);
        createUser("teammember188", "teammember188@gmail.com", "12345", "Member188", "Team", teammemberRole);
        createUser("teammember189", "teammember189@gmail.com", "12345", "Member189", "Team", teammemberRole);
        createUser("teammember190", "teammember190@gmail.com", "12345", "Member190", "Team", teammemberRole);
        createUser("teammember191", "teammember191@gmail.com", "12345", "Member191", "Team", teammemberRole);
        createUser("teammember192", "teammember192@gmail.com", "12345", "Member192", "Team", teammemberRole);
        createUser("teammember193", "teammember193@gmail.com", "12345", "Member193", "Team", teammemberRole);
        createUser("teammember194", "teammember194@gmail.com", "12345", "Member194", "Team", teammemberRole);
        createUser("teammember195", "teammember195@gmail.com", "12345", "Member195", "Team", teammemberRole);
        createUser("teammember196", "teammember196@gmail.com", "12345", "Member196", "Team", teammemberRole);
        createUser("teammember197", "teammember197@gmail.com", "12345", "Member197", "Team", teammemberRole);
        createUser("teammember198", "teammember198@gmail.com", "12345", "Member198", "Team", teammemberRole);
        createUser("teammember199", "teammember199@gmail.com", "12345", "Member199", "Team", teammemberRole);
        createUser("teammember200", "teammember200@gmail.com", "12345", "Member200", "Team", teammemberRole);

        log.info(">>> DEFAULT USERS CREATED SUCCESSFULLY");
    }

    private void createUser(String username, String email, String password, String firstName, String lastName, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
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
                        .enrollStartDate(LocalDateTime.of(2025, 5, 1, 8, 0))
                        .enrollEndDate(LocalDateTime.of(2025, 6, 5, 23, 59))
                        .description("A hackathon focused on AI and Machine Learning.")
                        .information("Participants will develop AI-powered solutions.")
                        .startDate(LocalDateTime.of(2024, 6, 10, 9, 0))
                        .endDate(LocalDateTime.of(2028, 6, 12, 18, 0))
                        .maxTeams(20)
                        .minTeamSize(3)
                        .maxTeamSize(5)
                        .contact("contact@ai-hackathon.com")
                        .category(CategoryStatus.CODING)
                        .organization(OrganizationStatus.FPTU)
                        .status(Status.ACTIVE)
                        .build(),

                Hackathon.builder()
                        .title("Blockchain Revolution")
                        .subTitle("Building the Next-Gen Blockchain Apps")
                        .bannerImageUrl("https://example.com/banner2.jpg")
                        .enrollStartDate(LocalDateTime.of(2025, 6, 1, 9, 0))
                        .enrollEndDate(LocalDateTime.of(2025, 7, 10, 23, 59))
                        .description("A hackathon focused on decentralized applications and smart contracts.")
                        .information("Teams will create blockchain-based solutions.")
                        .startDate(LocalDateTime.of(2024, 7, 15, 10, 0))
                        .endDate(LocalDateTime.of(2028, 7, 17, 19, 0))
                        .maxTeams(15)
                        .minTeamSize(2)
                        .maxTeamSize(6)
                        .contact("contact@blockchain-hack.com")
                        .category(CategoryStatus.CODING)
                        .organization(OrganizationStatus.FPTU)
                        .status(Status.ACTIVE)
                        .build(),

                Hackathon.builder()
                        .title("HealthTech Hackathon")
                        .subTitle("Innovating Healthcare Solutions")
                        .bannerImageUrl("https://example.com/banner3.jpg")
                        .enrollStartDate(LocalDateTime.of(2025, 7, 1, 8, 30))
                        .enrollEndDate(LocalDateTime.of(2025, 8, 15, 23, 59))
                        .description("A hackathon to develop digital health solutions.")
                        .information("Participants will design and prototype healthcare applications.")
                        .startDate(LocalDateTime.of(2024, 8, 20, 8, 30))
                        .endDate(LocalDateTime.of(2028, 8, 22, 17, 30))
                        .maxTeams(25)
                        .minTeamSize(3)
                        .maxTeamSize(6)
                        .contact("contact@healthtech-hack.com")
                        .category(CategoryStatus.CODING)
                        .organization(OrganizationStatus.FPTU)
                        .status(Status.ACTIVE)
                        .build(),

                Hackathon.builder()
                        .title("Cybersecurity Challenge")
                        .subTitle("Defending the Digital World")
                        .bannerImageUrl("https://example.com/banner4.jpg")
                        .enrollStartDate(LocalDateTime.of(2025, 8, 1, 9, 0))
                        .enrollEndDate(LocalDateTime.of(2025, 9, 1, 23, 59))
                        .description("A hackathon for ethical hackers and security researchers.")
                        .information("Teams will work on securing digital assets.")
                        .startDate(LocalDateTime.of(2024, 9, 5, 9, 0))
                        .endDate(LocalDateTime.of(2028, 9, 7, 18, 0))
                        .maxTeams(18)
                        .minTeamSize(2)
                        .maxTeamSize(5)
                        .contact("contact@cybersecurity-hack.com")
                        .category(CategoryStatus.CODING)
                        .organization(OrganizationStatus.FPTU)
                        .status(Status.ACTIVE)
                        .build(),

                Hackathon.builder()
                        .title("GreenTech Innovation")
                        .subTitle("Sustainable Solutions for a Greener Future")
                        .bannerImageUrl("https://example.com/banner5.jpg")
                        .enrollStartDate(LocalDateTime.of(2025, 9, 1, 10, 0))
                        .enrollEndDate(LocalDateTime.of(2025, 10, 5, 23, 59))
                        .description("A hackathon to promote eco-friendly and sustainable technologies.")
                        .information("Participants will create green-tech solutions to tackle climate change.")
                        .startDate(LocalDateTime.of(2024, 10, 12, 10, 0))
                        .endDate(LocalDateTime.of(2028, 10, 14, 16, 0))
                        .maxTeams(30)
                        .minTeamSize(3)
                        .maxTeamSize(7)
                        .contact("contact@greentech-hack.com")
                        .category(CategoryStatus.CODING)
                        .organization(OrganizationStatus.FPTU)
                        .status(Status.ACTIVE)
                        .build());

        hackathonRepository.saveAll(hackathons);
        log.info(">>> HACKATHONS CREATED SUCCESSFULLY");
    }
}
