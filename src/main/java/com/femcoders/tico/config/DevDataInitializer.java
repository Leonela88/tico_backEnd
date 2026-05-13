package com.femcoders.tico.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.femcoders.tico.entity.User;
import com.femcoders.tico.enums.UserRole;
import com.femcoders.tico.repository.UserRepository;

@Configuration
@Profile("dev")
public class DevDataInitializer {
    @Bean
    CommandLineRunner initDevData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() > 0) return;

            User admin = new User();
            admin.setName("Admin Dev");
            admin.setEmail("admin@tico.dev");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(UserRole.ADMIN));
            admin.setIsActive(true);

            User employee = new User();
            employee.setName("Empleado Dev");
            employee.setEmail("employee@tico.dev");
            employee.setPasswordHash(passwordEncoder.encode("employee123"));
            employee.setRoles(Set.of(UserRole.EMPLOYEE));
            employee.setIsActive(true);

            userRepository.saveAll(java.util.List.of(admin, employee));

            System.out.println(">>> Dev users created:");
            System.out.println(">>> ADMIN    → admin@tico.dev / admin123");
            System.out.println(">>> EMPLOYEE → employee@tico.dev / employee123");
        };
    }
}
