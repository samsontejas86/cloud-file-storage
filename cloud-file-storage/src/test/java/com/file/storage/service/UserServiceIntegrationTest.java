package com.file.storage.service;

import com.file.storage.dto.UserRegistrationRequest;
import com.file.storage.model.User;
import com.file.storage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("dev")
class UserServiceIntegrationTest {

    private static final DockerImageName MINIO_IMAGE = DockerImageName.parse("quay.io/minio/minio");

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Container
    private static final GenericContainer<?> minio = new GenericContainer<>(MINIO_IMAGE)
            .withCommand("server /data")
            .withEnv("MINIO_ROOT_USER", "user")
            .withEnv("MINIO_ROOT_PASSWORD", "minio_password")
            .withExposedPorts(9000);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
    }

    @Test
    void register_shouldSaveUserInDatabase() {
        var userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("user@gmail.com");
        userRegistrationRequest.setUsername("user");
        userRegistrationRequest.setPassword("password");

        userService.register(userRegistrationRequest);

        Optional<User> user = userRepository.findByUsername("user");
        assertTrue(user.isPresent(), "User should exist in the database");
        user.ifPresent(u -> {
            assertEquals("user@gmail.com", u.getEmail(), "Email should match");
            assertNotEquals("password", u.getPassword(), "Password should be hashed");
        });
    }

    @Test
    void register_sameUserTwice_shouldThrowException() {
        var userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setEmail("user@gmail.com");
        userRegistrationRequest.setUsername("user");
        userRegistrationRequest.setPassword("password");

        userService.register(userRegistrationRequest);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.register(userRegistrationRequest)
        );
        assertEquals(1, userRepository.findAll().size(), "User should be saved only once");
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }

    @DynamicPropertySource
    static void minioProperties(DynamicPropertyRegistry registry) {
        registry.add("minio.client.endpoint", () -> "http://127.0.0.1:" + minio.getMappedPort(9000));
        registry.add("minio.client.user", () -> "user");
        registry.add("minio.client.password", () -> "minio_password");
        registry.add("minio.bucket-name", () -> "user-files");
    }
}