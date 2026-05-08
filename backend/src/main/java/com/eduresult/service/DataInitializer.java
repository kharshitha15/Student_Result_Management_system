package com.eduresult.service;

import com.eduresult.model.Role;
import com.eduresult.model.User;
import com.eduresult.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create Admin
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);

            // Create Faculty
            User faculty = User.builder()
                    .username("faculty")
                    .password(passwordEncoder.encode("faculty123"))
                    .role(Role.ROLE_FACULTY)
                    .build();
            userRepository.save(faculty);
            
            System.out.println("Default users created: admin/admin123 and faculty/faculty123");
        }
    }
}
