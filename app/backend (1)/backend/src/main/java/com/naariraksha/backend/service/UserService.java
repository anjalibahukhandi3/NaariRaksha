package com.naariraksha.backend.service;

import com.naariraksha.backend.dto.LoginRequest;
import com.naariraksha.backend.dto.UserRegisterRequest;
import com.naariraksha.backend.entity.User;
import com.naariraksha.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(UserRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) // In production, use BCrypt!
                .build();

        userRepository.save(user);
        return "User " + request.getName() + " registered successfully";
    }

    public Map<String, Object> loginUser(LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(request.getPassword())) {
            User user = userOpt.get();
            return Map.of(
                "status", "success",
                "message", "Login successful",
                "userId", user.getId(),
                "name", user.getName()
            );
        }
        
        return Map.of("status", "error", "message", "Invalid email or password");
    }
}
