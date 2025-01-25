package com.hacof.identity.configs;

import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hacof.identity.entities.Role;
import com.hacof.identity.entities.User;
import com.hacof.identity.enums.RoleType;
import com.hacof.identity.repositories.RoleRepository;
import com.hacof.identity.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner() {
        return args -> {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                Role adminRole = roleRepository
                        .findByName(RoleType.ADMIN.name())
                        .orElseGet(() -> roleRepository.save(
                                Role.builder().name(RoleType.ADMIN.name()).build()));

                User adminUser = User.builder()
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("12345"))
                        .firstName("Admin")
                        .lastName("System")
                        .roles(Set.of(adminRole))
                        .build();

                userRepository.save(adminUser);
            }
        };
    }
}
