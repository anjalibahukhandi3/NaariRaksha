package com.naariraksha.backend.controller;

import com.naariraksha.backend.dto.LoginRequest;
import com.naariraksha.backend.dto.UserRegisterRequest;
import com.naariraksha.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Map<String, String> register(
            @Valid @RequestBody UserRegisterRequest request) {

        String message = userService.registerUser(request);
        return Map.of("message", message);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }

    @PostMapping("/sos")
    public Map<String, String> triggerSOS(@RequestBody Map<String, String> payload) {
        // System.out.println("SOS Received from: " + payload.get("email") + " at " + payload.get("location"));
        return Map.of("status", "SOS Alert Logged on Server", "timestamp", String.valueOf(System.currentTimeMillis()));
    }
}
