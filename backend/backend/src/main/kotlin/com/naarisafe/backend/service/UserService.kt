package com.naarisafe.backend.service

import com.naarisafe.backend.entity.User
import com.naarisafe.backend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun register(name: String, email: String, password: String) {
        if (userRepository.findByEmail(email) != null) {
            throw IllegalArgumentException("Email already registered")
        }

        val user = User(
            name = name,
            email = email,
            password = password   // hashing later
        )

        userRepository.save(user)
    }

    fun login(email: String, password: String): User {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Invalid email or password")

        if (user.password != password) {
            throw IllegalArgumentException("Invalid email or password")
        }

        return user
    }
}
