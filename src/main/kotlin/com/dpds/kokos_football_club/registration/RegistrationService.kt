package com.dpds.kokos_football_club.registration

import com.dpds.kokos_football_club.exception.DetailsException
import com.dpds.kokos_football_club.user.User
import com.dpds.kokos_football_club.user.UserRequest
import com.dpds.kokos_football_club.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RegistrationService @Autowired constructor(
    private val userService: UserService
) {

    fun registrationUser(userRequest: UserRequest): User {
        if(userService.existByLogin(userRequest.login)) {
            throw DetailsException("Пользователь уже существует")
        }
        return userService.createUser(userRequest)
    }
}