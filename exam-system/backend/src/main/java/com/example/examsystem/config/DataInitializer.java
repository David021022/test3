package com.example.examsystem.config;

import com.example.examsystem.entity.Role;
import com.example.examsystem.entity.SysUser;
import com.example.examsystem.repository.SysUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(SysUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            createOrFixUser(userRepository, passwordEncoder, "admin", Role.ADMIN);
            createOrFixUser(userRepository, passwordEncoder, "teacher1", Role.TEACHER);
            createOrFixUser(userRepository, passwordEncoder, "student1", Role.STUDENT);
        };
    }

    private void createOrFixUser(SysUserRepository userRepository, PasswordEncoder passwordEncoder, String username, Role role) {
        SysUser user = userRepository.findByUsername(username).orElseGet(() -> {
            SysUser u = new SysUser();
            u.setUsername(username);
            u.setCreatedAt(LocalDateTime.now());
            return u;
        });
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(role);
        user.setStatus(true);
        userRepository.save(user);
    }
}

