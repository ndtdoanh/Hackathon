package com.hacof.identity.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hacof.identity.constant.CategoryStatus;
import com.hacof.identity.constant.OrganizationStatus;
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
                            "DELETE_USER_HACKATHON",
                            "CREATE_NOTIFICATION",
                            "DELETE_NOTIFICATION",
                            "UPDATE_READ_STATUS",
                            "APPROVE_BLOG_POST",
                            "REJECT_BLOG_POST",
                            "DELETE_BLOG_POST"),
            "ORGANIZER",
                    Set.of(
                            "CREATE_USER_HACKATHON",
                            "DELETE_USER_HACKATHON",
                            "UPDATE_JUDGE_MENTOR_BY_ORGANIZER",
                            "CREATE_NOTIFICATION",
                            "DELETE_NOTIFICATION",
                            "UPDATE_READ_STATUS",
                            "CREATE_BLOG_POST",
                            "SUBMIT_BLOG_POST"),
            "JUDGE", Set.of(),
            "MENTOR", Set.of(),
            "GUEST", Set.of(),
            "TEAM_MEMBER", Set.of(),
            "TEAM_LEADER", Set.of());

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
                new Permission(
                        "DELETE_PERMISSION_FROM_ROLE",
                        "/api/v1/permissions/{roleId}/permissions/{permissionId}",
                        "DELETE",
                        "PERMISSIONS"),
                new Permission("CREATE_ROLE", "/api/v1/roles", "POST", "ROLES"),
                new Permission("UPDATE_ROLE", "/api/v1/roles/{Id}", "PUT", "ROLES"),
                new Permission("DELETE_ROLE", "/api/v1/roles/{Id}", "DELETE", "ROLES"),
                new Permission("UPDATE_JUDGE_MENTOR_BY_ORGANIZER", "/api/v1/users/organizer", "PUT", "USERS"),
                new Permission("DELETE_USER", "/api/v1/users/{Id}", "DELETE", "USERS"),
                new Permission("CREATE_USER_DEVICE", "/api/v1/user-devices", "POST", "USER_DEVICES"),
                new Permission("UPDATE_USER_DEVICE", "/api/v1/user-devices/{Id}", "PUT", "USER_DEVICES"),
                new Permission("DELETE_USER_DEVICE", "/api/v1/user-devices/{Id}", "DELETE", "USER_DEVICES"),
                new Permission("CREATE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks", "POST", "USER_DEVICE_TRACKS"),
                new Permission(
                        "UPDATE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks/{Id}", "PUT", "USER_DEVICE_TRACKS"),
                new Permission(
                        "DELETE_USER_DEVICE_TRACK", "/api/v1/user-device-tracks/{Id}", "DELETE", "USER_DEVICE_TRACKS"),
                new Permission("CREATE_USER_HACKATHON", "/api/v1/user-hackathons", "POST", "USER_HACKATHONS"),
                new Permission("DELETE_USER_HACKATHON", "/api/v1/user-hackathons/{Id}", "DELETE", "USER_HACKATHONS"),
                new Permission("CREATE_NOTIFICATION", "/api/v1/notifications", "POST", "NOTIFICATIONS"),
                new Permission("DELETE_NOTIFICATION", "/api/v1/notifications/{Id}", "DELETE", "NOTIFICATIONS"),
                new Permission(
                        "UPDATE_READ_STATUS",
                        "/api/v1/notifications/notification-deliveries/read-status",
                        "PUT",
                        "NOTIFICATIONS"),
                new Permission("CREATE_BLOG_POST", "/api/v1/blog-posts", "POST", "BLOG_POSTS"),
                new Permission("SUBMIT_BLOG_POST", "/api/v1/blog-posts/{id}/submit", "PUT", "BLOG_POSTS"),
                new Permission("APPROVE_BLOG_POST", "/api/v1/blog-posts/{id}/approve", "PUT", "BLOG_POSTS"),
                new Permission("REJECT_BLOG_POST", "/api/v1/blog-posts/{id}/reject", "PUT", "BLOG_POSTS"),
                new Permission("DELETE_BLOG_POST", "/api/v1/blog-posts/{id}", "DELETE", "BLOG_POSTS"));

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

        createUser("admin1", "12345", "Admin1", "System", adminRole);
        createUser("admin2", "12345", "Admin2", "System", adminRole);
        createUser("admin3", "12345", "Admin3", "System", adminRole);
        createUser("admin4", "12345", "Admin4", "System", adminRole);
        createUser("admin5", "12345", "Admin5", "System", adminRole);

        createUser("organizer1", "12345", "Organizer1", "System", organizerRole);
        createUser("organizer2", "12345", "Organizer2", "System", organizerRole);
        createUser("organizer3", "12345", "Organizer3", "System", organizerRole);
        createUser("organizer4", "12345", "Organizer4", "System", organizerRole);
        createUser("organizer5", "12345", "Organizer5", "System", organizerRole);
        createUser("organizer6", "12345", "Organizer6", "System", organizerRole);
        createUser("organizer7", "12345", "Organizer7", "System", organizerRole);
        createUser("organizer8", "12345", "Organizer8", "System", organizerRole);
        createUser("organizer9", "12345", "Organizer9", "System", organizerRole);
        createUser("organizer10", "12345", "Organizer10", "System", organizerRole);

        createUser("mentor1", "12345", "Mentor1", "System", mentorRole);
        createUser("mentor2", "12345", "Mentor2", "System", mentorRole);
        createUser("mentor3", "12345", "Mentor3", "System", mentorRole);
        createUser("mentor4", "12345", "Mentor4", "System", mentorRole);
        createUser("mentor5", "12345", "Mentor5", "System", mentorRole);
        createUser("mentor6", "12345", "Mentor6", "System", mentorRole);
        createUser("mentor7", "12345", "Mentor7", "System", mentorRole);
        createUser("mentor8", "12345", "Mentor8", "System", mentorRole);
        createUser("mentor9", "12345", "Mentor9", "System", mentorRole);
        createUser("mentor10", "12345", "Mentor10", "System", mentorRole);
        createUser("mentor11", "12345", "Mentor11", "System", mentorRole);
        createUser("mentor12", "12345", "Mentor12", "System", mentorRole);
        createUser("mentor13", "12345", "Mentor13", "System", mentorRole);
        createUser("mentor14", "12345", "Mentor14", "System", mentorRole);
        createUser("mentor15", "12345", "Mentor15", "System", mentorRole);
        createUser("mentor16", "12345", "Mentor16", "System", mentorRole);
        createUser("mentor17", "12345", "Mentor17", "System", mentorRole);
        createUser("mentor18", "12345", "Mentor18", "System", mentorRole);
        createUser("mentor19", "12345", "Mentor19", "System", mentorRole);
        createUser("mentor20", "12345", "Mentor20", "System", mentorRole);

        createUser("judge1", "12345", "Judge1", "System", judgeRole);
        createUser("judge2", "12345", "Judge2", "System", judgeRole);
        createUser("judge3", "12345", "Judge3", "System", judgeRole);
        createUser("judge4", "12345", "Judge4", "System", judgeRole);
        createUser("judge5", "12345", "Judge5", "System", judgeRole);
        createUser("judge6", "12345", "Judge6", "System", judgeRole);
        createUser("judge7", "12345", "Judge7", "System", judgeRole);
        createUser("judge8", "12345", "Judge8", "System", judgeRole);
        createUser("judge9", "12345", "Judge9", "System", judgeRole);
        createUser("judge10", "12345", "Judge10", "System", judgeRole);
        createUser("judge11", "12345", "Judge11", "System", judgeRole);
        createUser("judge12", "12345", "Judge12", "System", judgeRole);
        createUser("judge13", "12345", "Judge13", "System", judgeRole);
        createUser("judge14", "12345", "Judge14", "System", judgeRole);
        createUser("judge15", "12345", "Judge15", "System", judgeRole);
        createUser("judge16", "12345", "Judge16", "System", judgeRole);
        createUser("judge17", "12345", "Judge17", "System", judgeRole);
        createUser("judge18", "12345", "Judge18", "System", judgeRole);
        createUser("judge19", "12345", "Judge19", "System", judgeRole);
        createUser("judge20", "12345", "Judge20", "System", judgeRole);

        createUser("teammember1", "12345", "Member1", "Team", teammemberRole);
        createUser("teammember2", "12345", "Member2", "Team", teammemberRole);
        createUser("teammember3", "12345", "Member3", "Team", teammemberRole);
        createUser("teammember4", "12345", "Member4", "Team", teammemberRole);
        createUser("teammember5", "12345", "Member5", "Team", teammemberRole);
        createUser("teammember6", "12345", "Member6", "Team", teammemberRole);
        createUser("teammember7", "12345", "Member7", "Team", teammemberRole);
        createUser("teammember8", "12345", "Member8", "Team", teammemberRole);
        createUser("teammember9", "12345", "Member9", "Team", teammemberRole);
        createUser("teammember10", "12345", "Member10", "Team", teammemberRole);
        createUser("teammember11", "12345", "Member11", "Team", teammemberRole);
        createUser("teammember12", "12345", "Member12", "Team", teammemberRole);
        createUser("teammember13", "12345", "Member13", "Team", teammemberRole);
        createUser("teammember14", "12345", "Member14", "Team", teammemberRole);
        createUser("teammember15", "12345", "Member15", "Team", teammemberRole);
        createUser("teammember16", "12345", "Member16", "Team", teammemberRole);
        createUser("teammember17", "12345", "Member17", "Team", teammemberRole);
        createUser("teammember18", "12345", "Member18", "Team", teammemberRole);
        createUser("teammember19", "12345", "Member19", "Team", teammemberRole);
        createUser("teammember20", "12345", "Member20", "Team", teammemberRole);
        createUser("teammember21", "12345", "Member21", "Team", teammemberRole);
        createUser("teammember22", "12345", "Member22", "Team", teammemberRole);
        createUser("teammember23", "12345", "Member23", "Team", teammemberRole);
        createUser("teammember24", "12345", "Member24", "Team", teammemberRole);
        createUser("teammember25", "12345", "Member25", "Team", teammemberRole);
        createUser("teammember26", "12345", "Member26", "Team", teammemberRole);
        createUser("teammember27", "12345", "Member27", "Team", teammemberRole);
        createUser("teammember28", "12345", "Member28", "Team", teammemberRole);
        createUser("teammember29", "12345", "Member29", "Team", teammemberRole);
        createUser("teammember30", "12345", "Member30", "Team", teammemberRole);
        createUser("teammember31", "12345", "Member31", "Team", teammemberRole);
        createUser("teammember32", "12345", "Member32", "Team", teammemberRole);
        createUser("teammember33", "12345", "Member33", "Team", teammemberRole);
        createUser("teammember34", "12345", "Member34", "Team", teammemberRole);
        createUser("teammember35", "12345", "Member35", "Team", teammemberRole);
        createUser("teammember36", "12345", "Member36", "Team", teammemberRole);
        createUser("teammember37", "12345", "Member37", "Team", teammemberRole);
        createUser("teammember38", "12345", "Member38", "Team", teammemberRole);
        createUser("teammember39", "12345", "Member39", "Team", teammemberRole);
        createUser("teammember40", "12345", "Member40", "Team", teammemberRole);
        createUser("teammember41", "12345", "Member41", "Team", teammemberRole);
        createUser("teammember42", "12345", "Member42", "Team", teammemberRole);
        createUser("teammember43", "12345", "Member43", "Team", teammemberRole);
        createUser("teammember44", "12345", "Member44", "Team", teammemberRole);
        createUser("teammember45", "12345", "Member45", "Team", teammemberRole);
        createUser("teammember46", "12345", "Member46", "Team", teammemberRole);
        createUser("teammember47", "12345", "Member47", "Team", teammemberRole);
        createUser("teammember48", "12345", "Member48", "Team", teammemberRole);
        createUser("teammember49", "12345", "Member49", "Team", teammemberRole);
        createUser("teammember50", "12345", "Member50", "Team", teammemberRole);
        createUser("teammember51", "12345", "Member51", "Team", teammemberRole);
        createUser("teammember52", "12345", "Member52", "Team", teammemberRole);
        createUser("teammember53", "12345", "Member53", "Team", teammemberRole);
        createUser("teammember54", "12345", "Member54", "Team", teammemberRole);
        createUser("teammember55", "12345", "Member55", "Team", teammemberRole);
        createUser("teammember56", "12345", "Member56", "Team", teammemberRole);
        createUser("teammember57", "12345", "Member57", "Team", teammemberRole);
        createUser("teammember58", "12345", "Member58", "Team", teammemberRole);
        createUser("teammember59", "12345", "Member59", "Team", teammemberRole);
        createUser("teammember60", "12345", "Member60", "Team", teammemberRole);
        createUser("teammember61", "12345", "Member61", "Team", teammemberRole);
        createUser("teammember62", "12345", "Member62", "Team", teammemberRole);
        createUser("teammember63", "12345", "Member63", "Team", teammemberRole);
        createUser("teammember64", "12345", "Member64", "Team", teammemberRole);
        createUser("teammember65", "12345", "Member65", "Team", teammemberRole);
        createUser("teammember66", "12345", "Member66", "Team", teammemberRole);
        createUser("teammember67", "12345", "Member67", "Team", teammemberRole);
        createUser("teammember68", "12345", "Member68", "Team", teammemberRole);
        createUser("teammember69", "12345", "Member69", "Team", teammemberRole);
        createUser("teammember70", "12345", "Member70", "Team", teammemberRole);
        createUser("teammember71", "12345", "Member71", "Team", teammemberRole);
        createUser("teammember72", "12345", "Member72", "Team", teammemberRole);
        createUser("teammember73", "12345", "Member73", "Team", teammemberRole);
        createUser("teammember74", "12345", "Member74", "Team", teammemberRole);
        createUser("teammember75", "12345", "Member75", "Team", teammemberRole);
        createUser("teammember76", "12345", "Member76", "Team", teammemberRole);
        createUser("teammember77", "12345", "Member77", "Team", teammemberRole);
        createUser("teammember78", "12345", "Member78", "Team", teammemberRole);
        createUser("teammember79", "12345", "Member79", "Team", teammemberRole);
        createUser("teammember80", "12345", "Member80", "Team", teammemberRole);
        createUser("teammember81", "12345", "Member81", "Team", teammemberRole);
        createUser("teammember82", "12345", "Member82", "Team", teammemberRole);
        createUser("teammember83", "12345", "Member83", "Team", teammemberRole);
        createUser("teammember84", "12345", "Member84", "Team", teammemberRole);
        createUser("teammember85", "12345", "Member85", "Team", teammemberRole);
        createUser("teammember86", "12345", "Member86", "Team", teammemberRole);
        createUser("teammember87", "12345", "Member87", "Team", teammemberRole);
        createUser("teammember88", "12345", "Member88", "Team", teammemberRole);
        createUser("teammember89", "12345", "Member89", "Team", teammemberRole);
        createUser("teammember90", "12345", "Member90", "Team", teammemberRole);
        createUser("teammember91", "12345", "Member91", "Team", teammemberRole);
        createUser("teammember92", "12345", "Member92", "Team", teammemberRole);
        createUser("teammember93", "12345", "Member93", "Team", teammemberRole);
        createUser("teammember94", "12345", "Member94", "Team", teammemberRole);
        createUser("teammember95", "12345", "Member95", "Team", teammemberRole);
        createUser("teammember96", "12345", "Member96", "Team", teammemberRole);
        createUser("teammember97", "12345", "Member97", "Team", teammemberRole);
        createUser("teammember98", "12345", "Member98", "Team", teammemberRole);
        createUser("teammember99", "12345", "Member99", "Team", teammemberRole);
        createUser("teammember100", "12345", "Member100", "Team", teammemberRole);
        createUser("teammember101", "12345", "Member101", "Team", teammemberRole);
        createUser("teammember102", "12345", "Member102", "Team", teammemberRole);
        createUser("teammember103", "12345", "Member103", "Team", teammemberRole);
        createUser("teammember104", "12345", "Member104", "Team", teammemberRole);
        createUser("teammember105", "12345", "Member105", "Team", teammemberRole);
        createUser("teammember106", "12345", "Member106", "Team", teammemberRole);
        createUser("teammember107", "12345", "Member107", "Team", teammemberRole);
        createUser("teammember108", "12345", "Member108", "Team", teammemberRole);
        createUser("teammember109", "12345", "Member109", "Team", teammemberRole);
        createUser("teammember110", "12345", "Member110", "Team", teammemberRole);
        createUser("teammember111", "12345", "Member111", "Team", teammemberRole);
        createUser("teammember112", "12345", "Member112", "Team", teammemberRole);
        createUser("teammember113", "12345", "Member113", "Team", teammemberRole);
        createUser("teammember114", "12345", "Member114", "Team", teammemberRole);
        createUser("teammember115", "12345", "Member115", "Team", teammemberRole);
        createUser("teammember116", "12345", "Member116", "Team", teammemberRole);
        createUser("teammember117", "12345", "Member117", "Team", teammemberRole);
        createUser("teammember118", "12345", "Member118", "Team", teammemberRole);
        createUser("teammember119", "12345", "Member119", "Team", teammemberRole);
        createUser("teammember120", "12345", "Member120", "Team", teammemberRole);
        createUser("teammember121", "12345", "Member121", "Team", teammemberRole);
        createUser("teammember122", "12345", "Member122", "Team", teammemberRole);
        createUser("teammember123", "12345", "Member123", "Team", teammemberRole);
        createUser("teammember124", "12345", "Member124", "Team", teammemberRole);
        createUser("teammember125", "12345", "Member125", "Team", teammemberRole);
        createUser("teammember126", "12345", "Member126", "Team", teammemberRole);
        createUser("teammember127", "12345", "Member127", "Team", teammemberRole);
        createUser("teammember128", "12345", "Member128", "Team", teammemberRole);
        createUser("teammember129", "12345", "Member129", "Team", teammemberRole);
        createUser("teammember130", "12345", "Member130", "Team", teammemberRole);
        createUser("teammember131", "12345", "Member131", "Team", teammemberRole);
        createUser("teammember132", "12345", "Member132", "Team", teammemberRole);
        createUser("teammember133", "12345", "Member133", "Team", teammemberRole);
        createUser("teammember134", "12345", "Member134", "Team", teammemberRole);
        createUser("teammember135", "12345", "Member135", "Team", teammemberRole);
        createUser("teammember136", "12345", "Member136", "Team", teammemberRole);
        createUser("teammember137", "12345", "Member137", "Team", teammemberRole);
        createUser("teammember138", "12345", "Member138", "Team", teammemberRole);
        createUser("teammember139", "12345", "Member139", "Team", teammemberRole);
        createUser("teammember140", "12345", "Member140", "Team", teammemberRole);
        createUser("teammember141", "12345", "Member141", "Team", teammemberRole);
        createUser("teammember142", "12345", "Member142", "Team", teammemberRole);
        createUser("teammember143", "12345", "Member143", "Team", teammemberRole);
        createUser("teammember144", "12345", "Member144", "Team", teammemberRole);
        createUser("teammember145", "12345", "Member145", "Team", teammemberRole);
        createUser("teammember146", "12345", "Member146", "Team", teammemberRole);
        createUser("teammember147", "12345", "Member147", "Team", teammemberRole);
        createUser("teammember148", "12345", "Member148", "Team", teammemberRole);
        createUser("teammember149", "12345", "Member149", "Team", teammemberRole);
        createUser("teammember150", "12345", "Member150", "Team", teammemberRole);
        createUser("teammember151", "12345", "Member151", "Team", teammemberRole);
        createUser("teammember152", "12345", "Member152", "Team", teammemberRole);
        createUser("teammember153", "12345", "Member153", "Team", teammemberRole);
        createUser("teammember154", "12345", "Member154", "Team", teammemberRole);
        createUser("teammember155", "12345", "Member155", "Team", teammemberRole);
        createUser("teammember156", "12345", "Member156", "Team", teammemberRole);
        createUser("teammember157", "12345", "Member157", "Team", teammemberRole);
        createUser("teammember158", "12345", "Member158", "Team", teammemberRole);
        createUser("teammember159", "12345", "Member159", "Team", teammemberRole);
        createUser("teammember160", "12345", "Member160", "Team", teammemberRole);
        createUser("teammember161", "12345", "Member161", "Team", teammemberRole);
        createUser("teammember162", "12345", "Member162", "Team", teammemberRole);
        createUser("teammember163", "12345", "Member163", "Team", teammemberRole);
        createUser("teammember164", "12345", "Member164", "Team", teammemberRole);
        createUser("teammember165", "12345", "Member165", "Team", teammemberRole);
        createUser("teammember166", "12345", "Member166", "Team", teammemberRole);
        createUser("teammember167", "12345", "Member167", "Team", teammemberRole);
        createUser("teammember168", "12345", "Member168", "Team", teammemberRole);
        createUser("teammember169", "12345", "Member169", "Team", teammemberRole);
        createUser("teammember170", "12345", "Member170", "Team", teammemberRole);
        createUser("teammember171", "12345", "Member171", "Team", teammemberRole);
        createUser("teammember172", "12345", "Member172", "Team", teammemberRole);
        createUser("teammember173", "12345", "Member173", "Team", teammemberRole);
        createUser("teammember174", "12345", "Member174", "Team", teammemberRole);
        createUser("teammember175", "12345", "Member175", "Team", teammemberRole);
        createUser("teammember176", "12345", "Member176", "Team", teammemberRole);
        createUser("teammember177", "12345", "Member177", "Team", teammemberRole);
        createUser("teammember178", "12345", "Member178", "Team", teammemberRole);
        createUser("teammember179", "12345", "Member179", "Team", teammemberRole);
        createUser("teammember180", "12345", "Member180", "Team", teammemberRole);
        createUser("teammember181", "12345", "Member181", "Team", teammemberRole);
        createUser("teammember182", "12345", "Member182", "Team", teammemberRole);
        createUser("teammember183", "12345", "Member183", "Team", teammemberRole);
        createUser("teammember184", "12345", "Member184", "Team", teammemberRole);
        createUser("teammember185", "12345", "Member185", "Team", teammemberRole);
        createUser("teammember186", "12345", "Member186", "Team", teammemberRole);
        createUser("teammember187", "12345", "Member187", "Team", teammemberRole);
        createUser("teammember188", "12345", "Member188", "Team", teammemberRole);
        createUser("teammember189", "12345", "Member189", "Team", teammemberRole);
        createUser("teammember190", "12345", "Member190", "Team", teammemberRole);
        createUser("teammember191", "12345", "Member191", "Team", teammemberRole);
        createUser("teammember192", "12345", "Member192", "Team", teammemberRole);
        createUser("teammember193", "12345", "Member193", "Team", teammemberRole);
        createUser("teammember194", "12345", "Member194", "Team", teammemberRole);
        createUser("teammember195", "12345", "Member195", "Team", teammemberRole);
        createUser("teammember196", "12345", "Member196", "Team", teammemberRole);
        createUser("teammember197", "12345", "Member197", "Team", teammemberRole);
        createUser("teammember198", "12345", "Member198", "Team", teammemberRole);
        createUser("teammember199", "12345", "Member199", "Team", teammemberRole);
        createUser("teammember200", "12345", "Member200", "Team", teammemberRole);

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
                        .enrollStartDate(LocalDateTime.of(2025, 5, 1, 8, 0))
                        .enrollEndDate(LocalDateTime.of(2025, 6, 5, 23, 59))
                        .description("A hackathon focused on AI and Machine Learning.")
                        .information("Participants will develop AI-powered solutions.")
                        .startDate(LocalDateTime.of(2025, 6, 10, 9, 0))
                        .endDate(LocalDateTime.of(2025, 6, 12, 18, 0))
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
                        .startDate(LocalDateTime.of(2025, 7, 15, 10, 0))
                        .endDate(LocalDateTime.of(2025, 7, 17, 19, 0))
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
                        .startDate(LocalDateTime.of(2025, 8, 20, 8, 30))
                        .endDate(LocalDateTime.of(2025, 8, 22, 17, 30))
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
                        .startDate(LocalDateTime.of(2025, 9, 5, 9, 0))
                        .endDate(LocalDateTime.of(2025, 9, 7, 18, 0))
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
                        .startDate(LocalDateTime.of(2025, 10, 12, 10, 0))
                        .endDate(LocalDateTime.of(2025, 10, 14, 16, 0))
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
