package com.dpds.kokos_football_club.authentication

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Size

data class LoginRequest(
    @JsonProperty("login")
    @Size(max = 200)
    val login: String,
    @JsonProperty("password")
    @Size(min = 6, max = 32)
    val password: String
)

data class AuthenticationResponse(
    @JsonProperty("token")
    val token: String
)