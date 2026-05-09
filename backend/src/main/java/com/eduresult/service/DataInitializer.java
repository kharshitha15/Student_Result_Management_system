package com.eduresult.service;

import com.eduresult.model.Role;
import com.eduresult.model.User;
import com.eduresult.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create Admin if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
            logger.info("Default admin user created: admin/admin123");
        }

        // Create Faculty if not exists
        if (userRepository.findByUsername("faculty").isEmpty()) {
            User faculty = User.builder()
                    .username("faculty")
                    .password(passwordEncoder.encode("faculty123"))
                    .role(Role.ROLE_FACULTY)
                    .build();
            userRepository.save(faculty);
            logger.info("Default faculty user created: faculty/faculty123");
        }
        
        logger.info("Backend Data Initialization Complete.");
    }
}
