package com.naarisafe.backend.controller

import com.naarisafe.backend.dto.RegisterRequest
import com.naarisafe.backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.naarisafe.backend.dto.LoginRequest

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"])
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): ResponseEntity<String> {

        userService.register(
            request.name,
            request.email,
            request.password
        )

        return ResponseEntity.ok("Registration successful")
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): ResponseEntity<String> {

        userService.login(request.email, request.password)
        return ResponseEntity.ok("Login successful")
    }


}
