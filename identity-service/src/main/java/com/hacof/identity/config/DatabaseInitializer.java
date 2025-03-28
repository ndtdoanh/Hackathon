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
                            "CREATE_USER",
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "UPDATE_MY_INFO",
                            "DELETE_USER",
                            "ADD_EMAIL",
                            "VERIFY_EMAIL",
                            "CHANGE_PASSWORD",
                            "GET_LOGS",
                            "GET_LOG",
                            "SEARCH_LOGS",
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
                            "GET_CAMPUSES",
                            "GET_CAMPUS",
                            "CREATE_CAMPUS",
                            "UPDATE_CAMPUS",
                            "DELETE_CAMPUS",
                            "SEARCH_CAMPUSES",
                            "GET_ROUNDS",
                            "GET_ROUND",
                            "CREATE_ROUND",
                            "UPDATE_ROUND",
                            "DELETE_ROUND",
                            "ASSIGN_JUDGES_AND_MENTORS",
                            "ASSIGN_TASK_TO_MEMBER",
                            "GET_PASSED_TEAMS",
                            "GET_JUDGE_NAMES",
                            "GET_HACKATHONS",
                            "GET_HACKATHON",
                            "CREATE_HACKATHON",
                            "UPDATE_HACKATHON",
                            "DELETE_HACKATHON",
                            "CREATE_JUDGE",
                            "ASSIGN_JUDGE_TO_ROUND",
                            "GET_RESOURCES",
                            "CREATE_RESOURCE",
                            "UPDATE_RESOURCE",
                            "DELETE_RESOURCE",
                            "GET_RESOURCES_BY_ROUND",
                            "ASSIGN_TASK",
                            "GET_TASKS",
                            "GET_TASK",
                            "UPDATE_TASK",
                            "DELETE_TASK",
                            "CREATE_TASK",
                            "MOVE_TASK",
                            "CREATE_TEAM",
                            "ADD_MEMBER_TO_TEAM",
                            "ASSIGN_MENTOR_TO_TEAM",
                            "UPDATE_TEAM",
                            "REMOVE_MEMBER_FROM_TEAM",
                            "DELETE_TEAM",
                            "GET_TEAMS",
                            "CREATE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS",
                            "GET_BLOGCOMMENT",
                            "UPDATE_BLOGCOMMENT",
                            "DELETE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS_BY_POST",
                            "CREATE_BLOGPOST",
                            "GET_BLOGPOST",
                            "UPDATE_BLOGPOST",
                            "DELETE_BLOGPOST",
                            "GET_BLOGPOSTS",
                            "CREATE_FORUMCOMMENT",
                            "GET_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS",
                            "UPDATE_FORUMCOMMENT",
                            "DELETE_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS_BY_THREAD",
                            "CREATE_FORUMTHREAD",
                            "GET_FORUMTHREAD",
                            "GET_FORUMTHREADS",
                            "UPDATE_FORUMTHREAD",
                            "DELETE_FORUMTHREAD",
                            "CREATE_DEVICE",
                            "GET_DEVICES",
                            "GET_DEVICE",
                            "UPDATE_DEVICE",
                            "DELETE_DEVICE",
                            "ASSIGN_DEVICE",
                            "GET_USER_DEVICE",
                            "GET_USER_DEVICES",
                            "CREATE_USER_DEVICE_TRACK",
                            "GET_USER_DEVICE_TRACKS",
                            "CREATE_FEEDBACK",
                            "GET_FEEDBACKS",
                            "GET_FEEDBACK",
                            "DELETE_FEEDBACK",
                            "GET_FEEDBACKS_BY_TEAM",
                            "GET_FEEDBACKS_BY_HACKATHON",
                            "GET_FEEDBACKS_BY_MENTOR",
                            "CREATE_FEEDBACK_DETAIL",
                            "GET_FEEDBACK_DETAILS",
                            "GET_FEEDBACK_DETAIL",
                            "UPDATE_FEEDBACK_DETAIL",
                            "DELETE_FEEDBACK_DETAIL",
                            "GET_FEEDBACK_DETAILS_BY_FEEDBACK",
                            "GET_FEEDBACK_DETAILS_BY_MENTOR",
                            "GET_FEEDBACK_DETAILS_BY_HACKATHON",
                            "CREATE_BLOG_POST",
                            "GET_BLOG_POSTS",
                            "GET_BLOG_POST",
                            "UPDATE_BLOG_POST",
                            "DELETE_BLOG_POST",
                            "SUBMIT_BLOG_POST",
                            "APPROVE_BLOG_POST",
                            "REJECT_BLOG_POST"),
            "ORGANIZATION",
                    Set.of(
                            "CREATE_USER",
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "UPDATE_MY_INFO",
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
                            "UPLOAD_AVATAR",
                            "GET_CAMPUSES",
                            "GET_CAMPUS",
                            "SEARCH_CAMPUSES",
                            "GET_ROUNDS",
                            "GET_ROUND",
                            "ASSIGN_JUDGES_AND_MENTORS",
                            "ASSIGN_TASK_TO_MEMBER",
                            "GET_PASSED_TEAMS",
                            "GET_JUDGE_NAMES",
                            "GET_HACKATHONS",
                            "GET_HACKATHON",
                            "CREATE_HACKATHON",
                            "UPDATE_HACKATHON",
                            "DELETE_HACKATHON",
                            "CREATE_JUDGE",
                            "ASSIGN_JUDGE_TO_ROUND",
                            "GET_RESOURCES",
                            "GET_RESOURCES_BY_ROUND",
                            "ASSIGN_TASK",
                            "GET_TASKS",
                            "GET_TASK",
                            "UPDATE_TASK",
                            "DELETE_TASK",
                            "CREATE_TASK",
                            "MOVE_TASK",
                            "CREATE_TEAM",
                            "ADD_MEMBER_TO_TEAM",
                            "ASSIGN_MENTOR_TO_TEAM",
                            "UPDATE_TEAM",
                            "REMOVE_MEMBER_FROM_TEAM",
                            "DELETE_TEAM",
                            "GET_TEAMS",
                            "CREATE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS",
                            "GET_BLOGCOMMENT",
                            "UPDATE_BLOGCOMMENT",
                            "DELETE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS_BY_POST",
                            "CREATE_BLOGPOST",
                            "GET_BLOGPOST",
                            "UPDATE_BLOGPOST",
                            "DELETE_BLOGPOST",
                            "GET_BLOGPOSTS",
                            "CREATE_FORUMCOMMENT",
                            "GET_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS",
                            "UPDATE_FORUMCOMMENT",
                            "DELETE_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS_BY_THREAD",
                            "CREATE_FORUMTHREAD",
                            "GET_FORUMTHREAD",
                            "GET_FORUMTHREADS",
                            "UPDATE_FORUMTHREAD",
                            "DELETE_FORUMTHREAD"),
            "JUDGE",
                    Set.of(
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "UPDATE_MY_INFO",
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
                            "UPLOAD_AVATAR",
                            "GET_CAMPUSES",
                            "GET_CAMPUS",
                            "SEARCH_CAMPUSES",
                            "GET_ROUNDS",
                            "GET_ROUND",
                            "GET_HACKATHONS",
                            "GET_HACKATHON",
                            "GET_RESOURCES",
                            "GET_RESOURCES_BY_ROUND",
                            "GET_TASKS",
                            "GET_TASK",
                            "UPDATE_TASK",
                            "DELETE_TASK",
                            "CREATE_TASK",
                            "MOVE_TASK",
                            "CREATE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS",
                            "GET_BLOGCOMMENT",
                            "UPDATE_BLOGCOMMENT",
                            "DELETE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS_BY_POST",
                            "CREATE_BLOGPOST",
                            "GET_BLOGPOST",
                            "UPDATE_BLOGPOST",
                            "DELETE_BLOGPOST",
                            "GET_BLOGPOSTS",
                            "CREATE_FORUMCOMMENT",
                            "GET_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS",
                            "UPDATE_FORUMCOMMENT",
                            "DELETE_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS_BY_THREAD",
                            "CREATE_FORUMTHREAD",
                            "GET_FORUMTHREAD",
                            "GET_FORUMTHREADS",
                            "UPDATE_FORUMTHREAD",
                            "DELETE_FORUMTHREAD"),
            "MENTOR",
                    Set.of(
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "UPDATE_MY_INFO",
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
                            "UPLOAD_AVATAR",
                            "GET_CAMPUSES",
                            "GET_CAMPUS",
                            "SEARCH_CAMPUSES",
                            "GET_ROUNDS",
                            "GET_ROUND",
                            "GET_HACKATHONS",
                            "GET_HACKATHON",
                            "GET_RESOURCES",
                            "GET_RESOURCES_BY_ROUND",
                            "GET_TASKS",
                            "GET_TASK",
                            "UPDATE_TASK",
                            "DELETE_TASK",
                            "CREATE_TASK",
                            "MOVE_TASK",
                            "CREATE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS",
                            "GET_BLOGCOMMENT",
                            "UPDATE_BLOGCOMMENT",
                            "DELETE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS_BY_POST",
                            "CREATE_BLOGPOST",
                            "GET_BLOGPOST",
                            "UPDATE_BLOGPOST",
                            "DELETE_BLOGPOST",
                            "GET_BLOGPOSTS",
                            "CREATE_FORUMCOMMENT",
                            "GET_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS",
                            "UPDATE_FORUMCOMMENT",
                            "DELETE_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS_BY_THREAD",
                            "CREATE_FORUMTHREAD",
                            "GET_FORUMTHREAD",
                            "GET_FORUMTHREADS",
                            "UPDATE_FORUMTHREAD",
                            "DELETE_FORUMTHREAD"),
            "GUEST", Set.of(),
            "TEAM_MEMBER",
                    Set.of(
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "UPDATE_MY_INFO",
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
                            "UPLOAD_AVATAR",
                            "GET_CAMPUSES",
                            "GET_CAMPUS",
                            "SEARCH_CAMPUSES",
                            "GET_ROUNDS",
                            "GET_ROUND",
                            "GET_HACKATHONS",
                            "GET_HACKATHON",
                            "GET_RESOURCES",
                            "GET_RESOURCES_BY_ROUND",
                            "GET_TASKS",
                            "GET_TASK",
                            "UPDATE_TASK",
                            "DELETE_TASK",
                            "CREATE_TASK",
                            "MOVE_TASK",
                            "CREATE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS",
                            "GET_BLOGCOMMENT",
                            "UPDATE_BLOGCOMMENT",
                            "DELETE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS_BY_POST",
                            "CREATE_BLOGPOST",
                            "GET_BLOGPOST",
                            "UPDATE_BLOGPOST",
                            "DELETE_BLOGPOST",
                            "GET_BLOGPOSTS",
                            "CREATE_FORUMCOMMENT",
                            "GET_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS",
                            "UPDATE_FORUMCOMMENT",
                            "DELETE_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS_BY_THREAD",
                            "CREATE_FORUMTHREAD",
                            "GET_FORUMTHREAD",
                            "GET_FORUMTHREADS",
                            "UPDATE_FORUMTHREAD",
                            "DELETE_FORUMTHREAD"),
            "TEAM_LEADER",
                    Set.of(
                            "CREATE_PASSWORD",
                            "GET_USERS",
                            "GET_USER",
                            "GET_MY_INFO",
                            "UPDATE_MY_INFO",
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
                            "UPLOAD_AVATAR",
                            "GET_CAMPUSES",
                            "GET_CAMPUS",
                            "SEARCH_CAMPUSES",
                            "GET_ROUNDS",
                            "GET_ROUND",
                            "GET_HACKATHONS",
                            "GET_HACKATHON",
                            "GET_RESOURCES",
                            "GET_RESOURCES_BY_ROUND",
                            "GET_TASKS",
                            "GET_TASK",
                            "UPDATE_TASK",
                            "DELETE_TASK",
                            "CREATE_TASK",
                            "MOVE_TASK",
                            "CREATE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS",
                            "GET_BLOGCOMMENT",
                            "UPDATE_BLOGCOMMENT",
                            "DELETE_BLOGCOMMENT",
                            "GET_BLOGCOMMENTS_BY_POST",
                            "CREATE_BLOGPOST",
                            "GET_BLOGPOST",
                            "UPDATE_BLOGPOST",
                            "DELETE_BLOGPOST",
                            "GET_BLOGPOSTS",
                            "CREATE_FORUMCOMMENT",
                            "GET_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS",
                            "UPDATE_FORUMCOMMENT",
                            "DELETE_FORUMCOMMENT",
                            "GET_FORUMCOMMENTS_BY_THREAD",
                            "CREATE_FORUMTHREAD",
                            "GET_FORUMTHREAD",
                            "GET_FORUMTHREADS",
                            "UPDATE_FORUMTHREAD",
                            "DELETE_FORUMTHREAD"));

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
                new Permission("CREATE_USER", "/api/v1/users", "POST", "USERS"),
                new Permission("CREATE_PASSWORD", "/api/v1/users/create-password", "POST", "USERS"),
                new Permission("GET_USERS", "/api/v1/users", "GET", "USERS"),
                new Permission("GET_USER", "/api/v1/users/{Id}", "GET", "USERS"),
                new Permission("GET_MY_INFO", "/api/v1/users/my-info", "GET", "USERS"),
                new Permission("UPDATE_MY_INFO", "/api/v1/users/my-info", "PUT", "USERS"),
                new Permission("DELETE_USER", "/api/v1/users/{Id}", "DELETE", "USERS"),
                new Permission("ADD_EMAIL", "/api/v1/users/add-email", "POST", "USERS"),
                new Permission("VERIFY_EMAIL", "/api/v1/users/verify-email", "POST", "USERS"),
                new Permission("CHANGE_PASSWORD", "/api/v1/users/change-password", "POST", "USERS"),
                new Permission("GET_LOGS", "/api/v1/logs", "GET", "LOGS"),
                new Permission("GET_LOG", "/api/v1/logs/{id}", "GET", "LOGS"),
                new Permission("SEARCH_LOGS", "/api/v1/logs/search", "GET", "LOGS"),
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
                new Permission("GET_CAMPUSES", "/api/v1/campuses", "GET", "CAMPUSES"),
                new Permission("GET_CAMPUS", "/api/v1/campuses/{id}", "GET", "CAMPUSES"),
                new Permission("CREATE_CAMPUS", "/api/v1/campuses", "POST", "CAMPUSES"),
                new Permission("UPDATE_CAMPUS", "/api/v1/campuses", "PUT", "CAMPUSES"),
                new Permission("DELETE_CAMPUS", "/api/v1/campuses", "DELETE", "CAMPUSES"),
                new Permission("SEARCH_CAMPUSES", "/api/v1/campuses/search", "GET", "CAMPUSES"),
                new Permission("GET_ROUNDS", "/api/v1/competition-rounds", "GET", "ROUND"),
                new Permission("GET_ROUND", "/api/v1/competition-rounds/{id}", "GET", "ROUND"),
                new Permission("CREATE_ROUND", "/api/v1/competition-rounds", "POST", "ROUND"),
                new Permission("UPDATE_ROUND", "/api/v1/competition-rounds", "PUT", "ROUND"),
                new Permission("DELETE_ROUND", "/api/v1/competition-rounds", "DELETE", "ROUND"),
                new Permission("ASSIGN_JUDGES_AND_MENTORS", "/api/v1/competition-rounds/assign", "POST", "ROUND"),
                new Permission(
                        "ASSIGN_TASK_TO_MEMBER", "/api/v1/competition-rounds/{teamId}/assign-task", "POST", "ROUND"),
                new Permission("GET_PASSED_TEAMS", "/api/v1/competition-rounds/{roundId}/passed-teams", "GET", "ROUND"),
                new Permission("GET_JUDGE_NAMES", "/api/v1/competition-rounds/{roundId}/judges", "GET", "ROUND"),
                new Permission("GET_HACKATHONS", "/api/v1/hackathons", "GET", "HACKATHONS"),
                new Permission("GET_HACKATHON", "/api/v1/hackathons/{hackathonId}", "GET", "HACKATHONS"),
                new Permission("CREATE_HACKATHON", "/api/v1/hackathons", "POST", "HACKATHONS"),
                new Permission("UPDATE_HACKATHON", "/api/v1/hackathons/{hackathonId}", "PUT", "HACKATHONS"),
                new Permission("DELETE_HACKATHON", "/api/v1/hackathons/{hackathonId}", "DELETE", "HACKATHONS"),
                new Permission("CREATE_JUDGE", "/api/v1/judges", "POST", "JUDGES"),
                new Permission("ASSIGN_JUDGE_TO_ROUND", "/api/v1/judges/assign", "POST", "JUDGES"),
                new Permission("GET_RESOURCES", "/api/v1/resources", "GET", "RESOURCES"),
                new Permission("CREATE_RESOURCE", "/api/v1/resources", "POST", "RESOURCES"),
                new Permission("UPDATE_RESOURCE", "/api/v1/resources", "PUT", "RESOURCES"),
                new Permission("DELETE_RESOURCE", "/api/v1/resources", "DELETE", "RESOURCES"),
                new Permission("GET_RESOURCES_BY_ROUND", "/api/v1/resources/round", "GET", "RESOURCES"),
                new Permission("ASSIGN_TASK", "/api/v1/tasks", "POST", "TASKS"),
                new Permission("GET_TASKS", "/api/v1/tasks", "GET", "TASKS"),
                new Permission("GET_TASK", "/api/v1/tasks/{id}", "GET", "TASKS"),
                new Permission("CREATE_TASK", "/api/v1/tasks", "POST", "TASKS"),
                new Permission("UPDATE_TASK", "/api/v1/tasks/{taskId}", "PUT", "TASKS"),
                new Permission("DELETE_TASK", "/api/v1/tasks/{taskId}", "DELETE", "TASKS"),
                new Permission("MOVE_TASK", "/api/v1/tasks/update/{taskId}", "PUT", "TASKS"),
                new Permission("CREATE_TEAM", "/api/v1/teams/create", "POST", "TEAMS"),
                new Permission("ADD_MEMBER_TO_TEAM", "/api/v1/teams/add-member", "POST", "TEAMS"),
                new Permission("ASSIGN_MENTOR_TO_TEAM", "/api/v1/teams/assign-mentor", "POST", "TEAMS"),
                new Permission("UPDATE_TEAM", "/api/v1/teams/update", "PUT", "TEAMS"),
                new Permission("REMOVE_MEMBER_FROM_TEAM", "/api/v1/teams/remove-member", "DELETE", "TEAMS"),
                new Permission("DELETE_TEAM", "/api/v1/teams/delete", "DELETE", "TEAMS"),
                new Permission("GET_TEAMS", "/api/v1/teams", "GET", "TEAMS"),
                new Permission("CREATE_BLOGCOMMENT", "/api/v1/blogcomments", "POST", "BLOG_COMMENTS"),
                new Permission("GET_BLOGCOMMENTS", "/api/v1/blogcomments", "GET", "BLOG_COMMENTS"),
                new Permission("GET_BLOGCOMMENT", "/api/v1/blogcomments/{id}", "GET", "BLOG_COMMENTS"),
                new Permission("UPDATE_BLOGCOMMENT", "/api/v1/blogcomments/{id}", "PUT", "BLOG_COMMENTS"),
                new Permission("DELETE_BLOGCOMMENT", "/api/v1/blogcomments/{id}", "DELETE", "BLOG_COMMENTS"),
                new Permission(
                        "GET_BLOGCOMMENTS_BY_POST", "/api/v1/blogcomments/byPostId/{postId}", "GET", "BLOG_COMMENTS"),
                new Permission("CREATE_BLOGPOST", "/api/v1/blogposts", "POST", "BLOG_POSTS"),
                new Permission("GET_BLOGPOST", "/api/v1/blogposts/{id}", "GET", "BLOG_POSTS"),
                new Permission("UPDATE_BLOGPOST", "/api/v1/blogposts/{id}", "PUT", "BLOG_POSTS"),
                new Permission("DELETE_BLOGPOST", "/api/v1/blogposts/{id}", "DELETE", "BLOG_POSTS"),
                new Permission("GET_BLOGPOSTS", "/api/v1/blogposts", "GET", "BLOG_POSTS"),
                new Permission("CREATE_FORUMCOMMENT", "/api/v1/forumcomments", "POST", "FORUMCOMMENTS"),
                new Permission("GET_FORUMCOMMENT", "/api/v1/forumcomments/{id}", "GET", "FORUMCOMMENTS"),
                new Permission("GET_FORUMCOMMENTS", "/api/v1/forumcomments", "GET", "FORUMCOMMENTS"),
                new Permission("UPDATE_FORUMCOMMENT", "/api/v1/forumcomments/{id}", "PUT", "FORUMCOMMENTS"),
                new Permission("DELETE_FORUMCOMMENT", "/api/v1/forumcomments/{id}", "DELETE", "FORUMCOMMENTS"),
                new Permission(
                        "GET_FORUMCOMMENTS_BY_THREAD",
                        "/api/v1/forumcomments/thread/{threadId}",
                        "GET",
                        "FORUMCOMMENTS"),
                new Permission("CREATE_FORUMTHREAD", "/api/v1/forumthreads", "POST", "FORUMTHREADS"),
                new Permission("GET_FORUMTHREAD", "/api/v1/forumthreads/{id}", "GET", "FORUMTHREADS"),
                new Permission("GET_FORUMTHREADS", "/api/v1/forumthreads", "GET", "FORUMTHREADS"),
                new Permission("UPDATE_FORUMTHREAD", "/api/v1/forumthreads/{id}", "PUT", "FORUMTHREADS"),
                new Permission("DELETE_FORUMTHREAD", "/api/v1/forumthreads/{id}", "DELETE", "FORUMTHREADS"),
                new Permission("CREATE_DEVICE", "/api/v1/devices", "POST", "DEVICES"),
                new Permission("GET_DEVICE", "/api/v1/devices/{Id}", "GET", "DEVICES"),
                new Permission("GET_DEVICES", "/api/v1/devices", "GET", "DEVICES"),
                new Permission("UPDATE_DEVICE", "/api/v1/devices/{Id}", "PUT", "DEVICES"),
                new Permission("DELETE_DEVICE", "/api/v1/devices/{Id}", "DELETE", "DEVICES"),
                new Permission("ASSIGN_DEVICE", "/api/v1/user-devices", "POST", "USER_DEVICES"),
                new Permission("GET_USER_DEVICE", "/api/v1/user-devices/{Id}", "GET", "USER_DEVICES"),
                new Permission("GET_USER_DEVICES", "/api/v1/user-devices", "GET", "USER_DEVICES"),
                new Permission("CREATE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks", "POST", "USER_DEVICE_TRACKS"),
                new Permission("GET_USER_DEVICE_TRACKS", "/api/v1/user-device-tracks", "GET", "USER_DEVICE_TRACKS"),
                new Permission("CREATE_FEEDBACK", "/api/v1/feedbacks", "POST", "FEEDBACKS"),
                new Permission("GET_FEEDBACKS", "/api/v1/feedbacks", "GET", "FEEDBACKS"),
                new Permission("GET_FEEDBACK", "/api/v1/feedbacks/{id}", "GET", "FEEDBACKS"),
                new Permission("DELETE_FEEDBACK", "/api/v1/feedbacks/{id}", "DELETE", "FEEDBACKS"),
                new Permission("GET_FEEDBACKS_BY_TEAM", "/api/v1/feedbacks/by-team", "GET", "FEEDBACKS"),
                new Permission("GET_FEEDBACKS_BY_HACKATHON", "/api/v1/feedbacks/by-hackathon", "GET", "FEEDBACKS"),
                new Permission("GET_FEEDBACKS_BY_MENTOR", "/api/v1/feedbacks/by-mentor", "GET", "FEEDBACKS"),
                new Permission("CREATE_FEEDBACK_DETAIL", "/api/v1/feedback-details", "POST", "FEEDBACK_DETAILS"),
                new Permission("GET_FEEDBACK_DETAILS", "/api/v1/feedback-details", "GET", "FEEDBACK_DETAILS"),
                new Permission("GET_FEEDBACK_DETAIL", "/api/v1/feedback-details/{id}", "GET", "FEEDBACK_DETAILS"),
                new Permission("UPDATE_FEEDBACK_DETAIL", "/api/v1/feedback-details/{id}", "PUT", "FEEDBACK_DETAILS"),
                new Permission("DELETE_FEEDBACK_DETAIL", "/api/v1/feedback-details/{id}", "DELETE", "FEEDBACK_DETAILS"),
                new Permission(
                        "GET_FEEDBACK_DETAILS_BY_FEEDBACK",
                        "/api/v1/feedback-details/by-feedback",
                        "GET",
                        "FEEDBACK_DETAILS"),
                new Permission(
                        "GET_FEEDBACK_DETAILS_BY_MENTOR",
                        "/api/v1/feedback-details/by-mentor",
                        "GET",
                        "FEEDBACK_DETAILS"),
                new Permission(
                        "GET_FEEDBACK_DETAILS_BY_HACKATHON",
                        "/api/v1/feedback-details/by-hackathon",
                        "GET",
                        "FEEDBACK_DETAILS"),
                new Permission("CREATE_BLOG_POST", "/api/v1/blog-posts", "POST", "BLOG_POSTS"),
                new Permission("GET_BLOG_POSTS", "/api/v1/blog-posts", "GET", "BLOG_POSTS"),
                new Permission("GET_BLOG_POST", "/api/v1/blog-posts/{id}", "GET", "BLOG_POSTS"),
                new Permission("UPDATE_BLOG_POST", "/api/v1/blog-posts/{id}", "PUT", "BLOG_POSTS"),
                new Permission("DELETE_BLOG_POST", "/api/v1/blog-posts/{id}", "DELETE", "BLOG_POSTS"),
                new Permission("SUBMIT_BLOG_POST", "/api/v1/blog-posts/{id}/submit", "PUT", "BLOG_POSTS"),
                new Permission("APPROVE_BLOG_POST", "/api/v1/blog-posts/{id}/approve", "PUT", "BLOG_POSTS"),
                new Permission("REJECT_BLOG_POST", "/api/v1/blog-posts/{id}/reject", "PUT", "BLOG_POSTS"));

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
