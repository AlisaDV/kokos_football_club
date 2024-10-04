package com.dpds.kokos_football_club.security

import com.dpds.kokos_football_club.user.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
): UserDetailsService {
    override fun loadUserByUsername(username: String?): AuthenticatedUser {
        val user = username?.let { userRepository.findByLogin(username) }
        if (user == null) {
            throw UsernameNotFoundException("Доступ запрещён")
        }
        return AuthenticatedUser(user)
    }
}