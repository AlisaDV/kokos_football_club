package com.dpds.kokos_football_club.authentication

import com.dpds.kokos_football_club.security.JwtSigner
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authentication")
class AuthenticationController @Autowired constructor(
    private val jwtSigner: JwtSigner,
    private val authenticationService: AuthenticationService
) {
    @Operation(
        summary = "Логин",
        tags = ["Авторизация"]
    )
    @PostMapping("/login/")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): AuthenticationResponse {
        val user = authenticationService.login(loginRequest)
        return AuthenticationResponse(
            jwtSigner.createJwt(user.id.toString())
        )
    }

}