package com.naariraksha.backend.service;

import com.naariraksha.backend.dto.LoginRequest;
import com.naariraksha.backend.dto.UserRegisterRequest;
import com.naariraksha.backend.entity.User;
import com.naariraksha.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(UserRegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);

        return "User registered successfully";
    }

    public Map<String, Object> loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user != null &&
                user.getPassword().equals(request.getPassword())) {

            return Map.of(
                    "status", "success",
                    "userId", user.getId(),
                    "name", user.getName()
            );
        }

        return Map.of(
                "status", "error",
                "message", "Invalid credentials"
        );
    }
}
