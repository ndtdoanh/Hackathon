package com.hacof.identity.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hacof.identity.constant.Status;
import com.hacof.identity.entity.Permission;
import com.hacof.identity.entity.Role;
import com.hacof.identity.entity.RolePermission;
import com.hacof.identity.entity.User;
import com.hacof.identity.exception.AppException;
import com.hacof.identity.exception.ErrorCode;
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

                    "UPDATE_USER_STATUS",

                    "CREATE_BLOG_POST",
                    "SUBMIT_BLOG_POST",
                    "APPROVE_BLOG_POST",
                    "PUBLISH_BLOG_POST",
                    "UNPUBLISH_BLOG_POST",
                    "REJECT_BLOG_POST",
                    "UPDATE_BLOG_POST",
                    "DELETE_BLOG_POST",

                    "CREATE_FORUM_CATEGORY",
                    "UPDATE_FORUM_CATEGORY",
                    "DELETE_FORUM_CATEGORY",

                    "CREATE_FORUM_THREAD_ADMIN",
                    "UPDATE_FORUM_THREAD_ADMIN",
                    "REVIEW_THREAD_POST_REPORT",

                    "CREATE_HACKATHON",
                    "UPDATE_HACKATHON",
                    "DELETE_HACKATHON",

                    "CREATE_INDIVIDUAL_REGISTRATION",
                    "DELETE_INDIVIDUAL_REGISTRATION",

                    "CREATE_MENTORSHIP_REQUEST",
                    "DELETE_MENTORSHIP_REQUEST",

                    "CREATE_MENTORSHIP_SESSION_REQUEST",
                    "UPDATE_MENTORSHIP_SESSION_REQUEST",
                    "DELETE_MENTORSHIP_SESSION_REQUEST",

                    "CREATE_TEAM_REQUEST"

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
                    "DELETE_BY_TEAM_ROUND_AND_JUDGE",

                    "CREATE_FEEDBACK",
                    "DELETE_FEEDBACK",
                    "DELETE_FEEDBACK_DETAIL",

                    "CREATE_HACKATHON",
                    "UPDATE_HACKATHON",
                    "DELETE_HACKATHON",

                    "CREATE_HACKATHON_RESULT",
                    "UPDATE_HACKATHON_RESULT",
                    "DELETE_HACKATHON_RESULT",

                    "CREATE_BULK_HACKATHON_RESULT",
                    "UPDATE_BULK_HACKATHON_RESULT",

                    "UPDATE_INDIVIDUAL_REGISTRATION",
                    "UPDATE_BULK_INDIVIDUAL_REGISTRATION",

                    "CREATE_LOCATION",
                    "UPDATE_LOCATION",
                    "DELETE_LOCATION",

                    "CREATE_ROUND",
                    "UPDATE_ROUND",
                    "DELETE_ROUND",

                    "CREATE_SPONSORSHIP",
                    "UPDATE_SPONSORSHIP",
                    "DELETE_SPONSORSHIP",

                    "CREATE_SPONSORSHIP_HACKATHON",
                    "UPDATE_SPONSORSHIP_HACKATHON",
                    "DELETE_SPONSORSHIP_HACKATHON",

                    "CREATE_SPONSORSHIP_HACKATHON_DETAIL",
                    "UPDATE_SPONSORSHIP_HACKATHON_DETAIL",
                    "UPDATE_SPONSORSHIP_HACKATHON_DETAIL_FILES",
                    "DELETE_SPONSORSHIP_HACKATHON_DETAIL",

                    "REVIEW_TEAM_REQUEST",

                    "CREATE_BULK_TEAM",
                    "UPDATE_BULK_TEAM",

                    "CREATE_TEAM_ROUND",
                    "UPDATE_TEAM_ROUND",
                    "DELETE_TEAM_ROUND",

                    "UPDATE_BULK_TEAM_ROUND"
            ),
            "JUDGE",
            Set.of(
                    "CREATE_JUDGE_SUBMISSION",
                    "UPDATE_JUDGE_SUBMISSION",
                    "DELETE_JUDGE_SUBMISSION"
            ),
            "MENTOR",
            Set.of(
                    "APPROVE_MENTORSHIP_REQUEST",
                    "REJECT_MENTORSHIP_REQUEST",
                    "APPROVE_MENTORSHIP_SESSION_REQUEST",
                    "REJECT_MENTORSHIP_SESSION_REQUEST",
                    "UPDATE_MENTORSHIP_SESSION_REQUEST",

                    "UPDATE_MENTORSHIP_REQUEST"
            ),
            "TEAM_MEMBER",
            Set.of(
                    "CREATE_SUBMISSION",
                    "UPDATE_SUBMISSION",
                    "DELETE_SUBMISSION",

                    "CREATE_INDIVIDUAL_REGISTRATION",
                    "DELETE_INDIVIDUAL_REGISTRATION",

                    "CREATE_MENTORSHIP_REQUEST",
                    "DELETE_MENTORSHIP_REQUEST",

                    "CREATE_MENTORSHIP_SESSION_REQUEST",
                    "UPDATE_MENTORSHIP_SESSION_REQUEST",
                    "DELETE_MENTORSHIP_SESSION_REQUEST",

                    "CREATE_TEAM_REQUEST",
                    "RESPOND_TEAM_REQUEST",
                    "DELETE_TEAM_REQUEST"
            ),
            "TEAM_LEADER",
            Set.of("CREATE_SUBMISSION",
                    "UPDATE_SUBMISSION",
                    "DELETE_SUBMISSION",

                    "CREATE_MENTORSHIP_REQUEST",
                    "DELETE_MENTORSHIP_REQUEST",

                    "CREATE_MENTORSHIP_SESSION_REQUEST",
                    "UPDATE_MENTORSHIP_SESSION_REQUEST",
                    "DELETE_MENTORSHIP_SESSION_REQUEST",

                    "CREATE_TEAM_REQUEST",
                    "RESPOND_TEAM_REQUEST",
                    "DELETE_TEAM_REQUEST"
            ),
            "DEMO",
            Set.of("CREATE_SUBMISSION",
                    "CREATE_JUDGE_SUBMISSION",
                    "CREATE_BULK_INDIVIDUAL_REGISTRATION",
                    "UPDATE_BULK_INDIVIDUAL_REGISTRATION"
            ));

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
                new Permission("UPDATE_USER_STATUS", "/api/v1/users/{Id}/status", "PUT", "USERS"),

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
                new Permission("PUBLISH_BLOG_POST", "/api/v1/blog-posts/{id}/publish", "PUT", "BLOG_POSTS"),
                new Permission("UNPUBLISH_BLOG_POST", "/api/v1/blog-posts/{id}/unpublish", "PUT", "BLOG_POSTS"),
                new Permission("REJECT_BLOG_POST", "/api/v1/blog-posts/{id}/reject", "PUT", "BLOG_POSTS"),
                new Permission("UPDATE_BLOG_POST", "/api/v1/blog-posts/{id}", "PUT", "BLOG_POSTS"),
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
                new Permission("DELETE_SUBMISSION", "/api/v1/submissions/{id}", "DELETE", "SUBMISSIONS"),

                new Permission("CREATE_FEEDBACK", "/api/v1/feedbacks", "POST", "FEEDBACKS"),
                new Permission("DELETE_FEEDBACK", "/api/v1/feedbacks/{id}", "DELETE", "FEEDBACKS"),

                new Permission("DELETE_FEEDBACK_DETAIL", "/api/v1/feedback-details/{id}", "DELETE", "FEEDBACK_DETAILS"),

                new Permission("CREATE_FORUM_CATEGORY", "/api/v1/forum-categories", "POST", "FORUM_CATIGORIES"),
                new Permission("UPDATE_FORUM_CATEGORY", "/api/v1/forum-categories/{id}", "PUT", "FORUM_CATIGORIES"),
                new Permission("DELETE_FORUM_CATEGORY", "/api/v1/forum-categories/{id}", "DELETE", "FORUM_CATIGORIES"),

                new Permission("CREATE_FORUM_THREAD_ADMIN", "/api/v1/forum-threads/admin", "POST", "FORUM_THREADS"),
                new Permission("UPDATE_FORUM_THREAD_ADMIN", "/api/v1/forum-threads/admin/{id}", "PUT", "FORUM_THREADS"),
                new Permission("REVIEW_THREAD_POST_REPORT", "/api/v1/forum-threads/review/{id}", "PUT", "FORUM_THREADS"),

                new Permission("CREATE_HACKATHON", "/api/v1/hackathons", "POST", "HACKATHONS"),
                new Permission("UPDATE_HACKATHON", "/api/v1/hackathons", "PUT", "HACKATHONS"),
                new Permission("DELETE_HACKATHON", "/api/v1/hackathons/{id}", "DELETE", "HACKATHONS"),

                new Permission("CREATE_HACKATHON_RESULT", "/api/v1/hackathons/results", "POST", "HACKATHON_RESULTS"),
                new Permission("UPDATE_HACKATHON_RESULT", "/api/v1/hackathons/results", "PUT", "HACKATHON_RESULTS"),
                new Permission("DELETE_HACKATHON_RESULT", "/api/v1/hackathons/results/{id}", "DELETE", "HACKATHON_RESULTS"),

                new Permission("CREATE_BULK_HACKATHON_RESULT", "/api/v1/hackathons/results/bulk-create", "POST", "HACKATHON_RESULTS"),
                new Permission("UPDATE_BULK_HACKATHON_RESULT", "/api/v1/hackathons/results/bulk-update", "PUT", "HACKATHON_RESULTS"),

                new Permission("CREATE_INDIVIDUAL_REGISTRATION", "/api/v1/individuals", "POST", "INDIVIDUAL_REGISTRATIONS"),
                new Permission("UPDATE_INDIVIDUAL_REGISTRATION", "/api/v1/individuals", "PUT", "INDIVIDUAL_REGISTRATIONS"),
                new Permission("CREATE_BULK_INDIVIDUAL_REGISTRATION", "/api/v1/individuals/bulk", "POST", "INDIVIDUAL_REGISTRATIONS"),
                new Permission("UPDATE_BULK_INDIVIDUAL_REGISTRATION", "/api/v1/individuals/bulk-update", "PUT", "INDIVIDUAL_REGISTRATIONS"),
                new Permission("DELETE_INDIVIDUAL_REGISTRATION", "/api/v1/individuals/{id}", "DELETE", "INDIVIDUAL_REGISTRATIONS"),

                new Permission("CREATE_LOCATION", "/api/v1/locations", "POST", "LOCATIONS"),
                new Permission("UPDATE_LOCATION", "/api/v1/locations", "PUT", "LOCATIONS"),
                new Permission("DELETE_LOCATION", "/api/v1/locations/{id}", "DELETE", "LOCATIONS"),

                new Permission("CREATE_MENTORSHIP_REQUEST", "/api/v1/mentorships", "POST", "MENTORSHIP_REQUESTS"),
                new Permission("APPROVE_MENTORSHIP_REQUEST", "/api/v1/mentorships/approve", "POST", "MENTORSHIP_REQUESTS"),
                new Permission("REJECT_MENTORSHIP_REQUEST", "/api/v1/mentorships/reject", "POST", "MENTORSHIP_REQUESTS"),
                new Permission("UPDATE_MENTORSHIP_REQUEST", "/api/v1/mentorships", "PUT", "MENTORSHIP_REQUESTS"),
                new Permission("DELETE_MENTORSHIP_REQUEST", "/api/v1/mentorships/{id}", "DELETE", "MENTORSHIP_REQUESTS"),

                new Permission("CREATE_MENTORSHIP_SESSION_REQUEST", "/api/v1/mentorships/sessions", "POST", "MENTORSHIP_SESSION_REQUESTS"),
                new Permission("UPDATE_MENTORSHIP_SESSION_REQUEST", "/api/v1/mentorships/sessions", "PUT", "MENTORSHIP_SESSION_REQUESTS"),
                new Permission("APPROVE_MENTORSHIP_SESSION_REQUEST", "/api/v1/mentorships/sessions/approve", "POST", "MENTORSHIP_SESSION_REQUESTS"),
                new Permission("REJECT_MENTORSHIP_SESSION_REQUEST", "/api/v1/mentorships/sessions/reject", "POST", "MENTORSHIP_SESSION_REQUESTS"),
                new Permission("DELETE_MENTORSHIP_SESSION_REQUEST", "/api/v1/mentorships/sessions/{id}", "DELETE", "MENTORSHIP_SESSION_REQUESTS"),

                new Permission("DELETE_MENTOR_TEAM", "/api/v1/mentor-teams/{id}", "DELETE", "MENTOR_TEAMS"),

                new Permission("CREATE_ROUND", "/api/v1/rounds", "POST", "ROUNDS"),
                new Permission("UPDATE_ROUND", "/api/v1/rounds", "PUT", "ROUNDS"),
                new Permission("DELETE_ROUND", "/api/v1/rounds/{id}", "DELETE", "ROUNDS"),

                new Permission("CREATE_SPONSORSHIP", "/api/v1/sponsorships", "POST", "SPONSORSHIPS"),
                new Permission("UPDATE_SPONSORSHIP", "/api/v1/sponsorships", "PUT", "SPONSORSHIPS"),
                new Permission("DELETE_SPONSORSHIP", "/api/v1/sponsorships/{id}", "DELETE", "SPONSORSHIPS"),

                new Permission("CREATE_SPONSORSHIP_HACKATHON", "/api/v1/sponsorships/hackathons", "POST", "SPONSORSHIP_HACKATHONS"),
                new Permission("UPDATE_SPONSORSHIP_HACKATHON", "/api/v1/sponsorships/hackathons", "PUT", "SPONSORSHIP_HACKATHONS"),
                new Permission("DELETE_SPONSORSHIP_HACKATHON", "/api/v1/sponsorships/hackathons/{id}", "DELETE", "SPONSORSHIP_HACKATHONS"),

                new Permission("CREATE_SPONSORSHIP_HACKATHON_DETAIL", "/api/v1/sponsorships/details", "POST", "SPONSORSHIP_HACKATHON_DETAILS"),
                new Permission("UPDATE_SPONSORSHIP_HACKATHON_DETAIL", "/api/v1/sponsorships/details/update-info/{id}", "PUT", "SPONSORSHIP_HACKATHON_DETAILS"),
                new Permission("UPDATE_SPONSORSHIP_HACKATHON_DETAIL_FILES", "/api/v1/sponsorships/details/update-files/{id}", "PUT", "SPONSORSHIP_HACKATHON_DETAILS"),
                new Permission("DELETE_SPONSORSHIP_HACKATHON_DETAIL", "/api/v1/sponsorships/details/{id}", "DELETE", "SPONSORSHIP_HACKATHON_DETAILS"),

                new Permission("CREATE_TEAM_REQUEST", "/api/v1/teams/requests", "POST", "TEAM_REQUESTS"),
                new Permission("RESPOND_TEAM_REQUEST", "/api/v1/teams/requests/respond", "POST", "TEAM_REQUESTS"),
                new Permission("REVIEW_TEAM_REQUEST", "/api/v1/teams/requests/review", "POST", "TEAM_REQUESTS"),
                new Permission("DELETE_TEAM_REQUEST", "/api/v1/teams/requests/{id}", "DELETE", "TEAM_REQUESTS"),
                new Permission("CREATE_BULK_TEAM", "/api/v1/teams/bulk", "POST", "TEAM_REQUESTS"),
                new Permission("UPDATE_BULK_TEAM", "/api/v1/teams", "PUT", "TEAM_REQUESTS"),
                new Permission("DELETE_TEAM", "/api/v1/teams/{id}", "DELETE", "TEAM_REQUESTS"),

                new Permission("CREATE_TEAM_ROUND", "/api/v1/team-rounds", "POST", "TEAM_ROUNDS"),
                new Permission("UPDATE_TEAM_ROUND", "/api/v1/team-rounds", "PUT", "TEAM_ROUNDS"),
                new Permission("DELETE_TEAM_ROUND", "/api/v1/team-rounds/{id}", "DELETE", "TEAM_ROUNDS"),
                new Permission("UPDATE_BULK_TEAM_ROUND", "/api/v1/team-rounds/bulk", "PUT", "TEAM_ROUNDS")
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
        Role demoRole = getRole("DEMO");
        Role adminRole = getRole("ADMIN");
        Role organizerRole = getRole("ORGANIZER");
        Role mentorRole = getRole("MENTOR");
        Role judgeRole = getRole("JUDGE");
        Role teammemberRole = getRole("TEAM_MEMBER");

        createUser("demo", "demo@gmail.com", "12345", "Demo", "System", demoRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");

        createUser("admin1", "admin1@gmail.com", "12345", "Admin1", "System", adminRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("admin2", "admin2@gmail.com", "12345", "Admin2", "System", adminRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("admin3", "admin3@gmail.com", "12345", "Admin3", "System", adminRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("admin4", "admin4@gmail.com", "12345", "Admin4", "System", adminRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("admin5", "admin5@gmail.com", "12345", "Admin5", "System", adminRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");

        createUser("organizer1", "organizer1@gmail.com", "12345", "Organizer1", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer2", "organizer2@gmail.com", "12345", "Organizer2", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer3", "organizer3@gmail.com", "12345", "Organizer3", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer4", "organizer4@gmail.com", "12345", "Organizer4", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer5", "organizer5@gmail.com", "12345", "Organizer5", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer6", "organizer6@gmail.com", "12345", "Organizer6", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer7", "organizer7@gmail.com", "12345", "Organizer7", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer8", "organizer8@gmail.com", "12345", "Organizer8", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer9", "organizer9@gmail.com", "12345", "Organizer9", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("organizer10", "organizer10@gmail.com", "12345", "Organizer10", "System", organizerRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");


        createUser("mentor1", "mentor1@gmail.com", "12345", "Mentor1", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor2", "mentor2@gmail.com", "12345", "Mentor2", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor3", "mentor3@gmail.com", "12345", "Mentor3", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor4", "mentor4@gmail.com", "12345", "Mentor4", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor5", "mentor5@gmail.com", "12345", "Mentor5", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor6", "mentor6@gmail.com", "12345", "Mentor6", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor7", "mentor7@gmail.com", "12345", "Mentor7", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor8", "mentor8@gmail.com", "12345", "Mentor8", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor9", "mentor9@gmail.com", "12345", "Mentor9", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor10", "mentor10@gmail.com", "12345", "Mentor10", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor11", "mentor11@gmail.com", "12345", "Mentor11", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor12", "mentor12@gmail.com", "12345", "Mentor12", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor13", "mentor13@gmail.com", "12345", "Mentor13", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor14", "mentor14@gmail.com", "12345", "Mentor14", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor15", "mentor15@gmail.com", "12345", "Mentor15", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor16", "mentor16@gmail.com", "12345", "Mentor16", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor17", "mentor17@gmail.com", "12345", "Mentor17", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor18", "mentor18@gmail.com", "12345", "Mentor18", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor19", "mentor19@gmail.com", "12345", "Mentor19", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("mentor20", "mentor20@gmail.com", "12345", "Mentor20", "System", mentorRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");

        createUser("judge1", "judge1@gmail.com", "12345", "Judge1", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge2", "judge2@gmail.com", "12345", "Judge2", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge3", "judge3@gmail.com", "12345", "Judge3", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge4", "judge4@gmail.com", "12345", "Judge4", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge5", "judge5@gmail.com", "12345", "Judge5", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge6", "judge6@gmail.com", "12345", "Judge6", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge7", "judge7@gmail.com", "12345", "Judge7", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge8", "judge8@gmail.com", "12345", "Judge8", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge9", "judge9@gmail.com", "12345", "Judge9", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge10", "judge10@gmail.com", "12345", "Judge10", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge11", "judge11@gmail.com", "12345", "Judge11", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge12", "judge12@gmail.com", "12345", "Judge12", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge13", "judge13@gmail.com", "12345", "Judge13", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge14", "judge14@gmail.com", "12345", "Judge14", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge15", "judge15@gmail.com", "12345", "Judge15", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge16", "judge16@gmail.com", "12345", "Judge16", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge17", "judge17@gmail.com", "12345", "Judge17", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge18", "judge18@gmail.com", "12345", "Judge18", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge19", "judge19@gmail.com", "12345", "Judge19", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("judge20", "judge20@gmail.com", "12345", "Judge20", "System", judgeRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");

        createUser("teammember1", "teammember1@gmail.com", "12345", "Member1", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember2", "teammember2@gmail.com", "12345", "Member2", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember3", "teammember3@gmail.com", "12345", "Member3", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember4", "teammember4@gmail.com", "12345", "Member4", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember5", "teammember5@gmail.com", "12345", "Member5", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember6", "teammember6@gmail.com", "12345", "Member6", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember7", "teammember7@gmail.com", "12345", "Member7", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember8", "teammember8@gmail.com", "12345", "Member8", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember9", "teammember9@gmail.com", "12345", "Member9", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember10", "teammember10@gmail.com", "12345", "Member10", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember11", "teammember11@gmail.com", "12345", "Member11", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember12", "teammember12@gmail.com", "12345", "Member12", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember13", "teammember13@gmail.com", "12345", "Member13", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember14", "teammember14@gmail.com", "12345", "Member14", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember15", "teammember15@gmail.com", "12345", "Member15", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember16", "teammember16@gmail.com", "12345", "Member16", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember17", "teammember17@gmail.com", "12345", "Member17", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember18", "teammember18@gmail.com", "12345", "Member18", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember19", "teammember19@gmail.com", "12345", "Member19", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember20", "teammember20@gmail.com", "12345", "Member20", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember21", "teammember21@gmail.com", "12345", "Member21", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember22", "teammember22@gmail.com", "12345", "Member22", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember23", "teammember23@gmail.com", "12345", "Member23", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember24", "teammember24@gmail.com", "12345", "Member24", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember25", "teammember25@gmail.com", "12345", "Member25", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember26", "teammember26@gmail.com", "12345", "Member26", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember27", "teammember27@gmail.com", "12345", "Member27", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember28", "teammember28@gmail.com", "12345", "Member28", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember29", "teammember29@gmail.com", "12345", "Member29", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember30", "teammember30@gmail.com", "12345", "Member30", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember31", "teammember31@gmail.com", "12345", "Member31", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember32", "teammember32@gmail.com", "12345", "Member32", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember33", "teammember33@gmail.com", "12345", "Member33", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember34", "teammember34@gmail.com", "12345", "Member34", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember35", "teammember35@gmail.com", "12345", "Member35", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember36", "teammember36@gmail.com", "12345", "Member36", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember37", "teammember37@gmail.com", "12345", "Member37", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember38", "teammember38@gmail.com", "12345", "Member38", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember39", "teammember39@gmail.com", "12345", "Member39", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember40", "teammember40@gmail.com", "12345", "Member40", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember41", "teammember41@gmail.com", "12345", "Member41", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember42", "teammember42@gmail.com", "12345", "Member42", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember43", "teammember43@gmail.com", "12345", "Member43", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember44", "teammember44@gmail.com", "12345", "Member44", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember45", "teammember45@gmail.com", "12345", "Member45", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember46", "teammember46@gmail.com", "12345", "Member46", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember47", "teammember47@gmail.com", "12345", "Member47", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember48", "teammember48@gmail.com", "12345", "Member48", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember49", "teammember49@gmail.com", "12345", "Member49", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember50", "teammember50@gmail.com", "12345", "Member50", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");

        createUser("teammember51", "teammember51@gmail.com", "12345", "Member51", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember52", "teammember52@gmail.com", "12345", "Member52", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember53", "teammember53@gmail.com", "12345", "Member53", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember54", "teammember54@gmail.com", "12345", "Member54", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember55", "teammember55@gmail.com", "12345", "Member55", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember56", "teammember56@gmail.com", "12345", "Member56", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember57", "teammember57@gmail.com", "12345", "Member57", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember58", "teammember58@gmail.com", "12345", "Member58", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember59", "teammember59@gmail.com", "12345", "Member59", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember60", "teammember60@gmail.com", "12345", "Member60", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember61", "teammember61@gmail.com", "12345", "Member61", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember62", "teammember62@gmail.com", "12345", "Member62", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember63", "teammember63@gmail.com", "12345", "Member63", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember64", "teammember64@gmail.com", "12345", "Member64", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember65", "teammember65@gmail.com", "12345", "Member65", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember66", "teammember66@gmail.com", "12345", "Member66", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember67", "teammember67@gmail.com", "12345", "Member67", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember68", "teammember68@gmail.com", "12345", "Member68", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember69", "teammember69@gmail.com", "12345", "Member69", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember70", "teammember70@gmail.com", "12345", "Member70", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember71", "teammember71@gmail.com", "12345", "Member71", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember72", "teammember72@gmail.com", "12345", "Member72", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember73", "teammember73@gmail.com", "12345", "Member73", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember74", "teammember74@gmail.com", "12345", "Member74", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember75", "teammember75@gmail.com", "12345", "Member75", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember76", "teammember76@gmail.com", "12345", "Member76", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember77", "teammember77@gmail.com", "12345", "Member77", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember78", "teammember78@gmail.com", "12345", "Member78", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember79", "teammember79@gmail.com", "12345", "Member79", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember80", "teammember80@gmail.com", "12345", "Member80", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember81", "teammember81@gmail.com", "12345", "Member81", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember82", "teammember82@gmail.com", "12345", "Member82", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember83", "teammember83@gmail.com", "12345", "Member83", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember84", "teammember84@gmail.com", "12345", "Member84", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember85", "teammember85@gmail.com", "12345", "Member85", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember86", "teammember86@gmail.com", "12345", "Member86", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember87", "teammember87@gmail.com", "12345", "Member87", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember88", "teammember88@gmail.com", "12345", "Member88", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember89", "teammember89@gmail.com", "12345", "Member89", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember90", "teammember90@gmail.com", "12345", "Member90", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember91", "teammember91@gmail.com", "12345", "Member91", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember92", "teammember92@gmail.com", "12345", "Member92", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember93", "teammember93@gmail.com", "12345", "Member93", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember94", "teammember94@gmail.com", "12345", "Member94", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember95", "teammember95@gmail.com", "12345", "Member95", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember96", "teammember96@gmail.com", "12345", "Member96", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember97", "teammember97@gmail.com", "12345", "Member97", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember98", "teammember98@gmail.com", "12345", "Member98", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember99", "teammember99@gmail.com", "12345", "Member99", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");
        createUser("teammember100", "teammember100@gmail.com", "12345", "Member100", "Team", teammemberRole, "https://greenscapehub-media.s3.ap-southeast-1.amazonaws.com/hacofpt/504c1e5a-bc1f-4fe7-8905-d3bbbb12cabd_smiling-young-man-illustration_1308-174669.avif");

        log.info(">>> DEFAULT USERS CREATED SUCCESSFULLY");
    }

    private void createUser(
            String username, String email, String password, String firstName, String lastName, Role role, String avatarUrl) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setVerified(false);
        user.setStatus(Status.ACTIVE);
        user.addRole(role);
        user.setAvatarUrl(avatarUrl);

        userRepository.save(user);
    }

    private Role getRole(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
    }
}
