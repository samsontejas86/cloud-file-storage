package com.file.storage.service;

import com.file.storage.dto.UserRegistrationRequest;
import com.file.storage.model.User;
import com.file.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.file.storage.model.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRegistrationRequest userRegistrationRequest) {
        User user = new User(
                userRegistrationRequest.getUsername(),
                passwordEncoder.encode(userRegistrationRequest.getPassword()),
                userRegistrationRequest.getEmail(),
                Set.of(ROLE_USER)
        );

        userRepository.save(user);
    }
}
