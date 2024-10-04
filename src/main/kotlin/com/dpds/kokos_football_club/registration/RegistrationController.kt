package com.dpds.kokos_football_club.registration

import com.dpds.kokos_football_club.user.UserRequest
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/registration")
class RegistrationController @Autowired constructor(
    private val registrationService: RegistrationService
){

    @Operation(
        summary = "Зарегистрироваться",
        tags = ["Регистрация"]
    )
    @PostMapping("/")
    fun registrationUser(
        @RequestBody userRequest: UserRequest
    ): RegistrationResponse {
        val user = registrationService.registrationUser(userRequest)
        return RegistrationResponse(user)
    }
}