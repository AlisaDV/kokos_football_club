package com.dpds.kokos_football_club.authentication

import com.dpds.kokos_football_club.exception.ForbiddenException
import com.dpds.kokos_football_club.user.User
import com.dpds.kokos_football_club.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService @Autowired constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun login(request: LoginRequest): User {
        val user = userRepository.findByLogin(request.login) ?: throw ForbiddenException("Неправильный логин или пароль")
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw ForbiddenException("Неправильный логин или пароль")
        }
        return user
    }
}