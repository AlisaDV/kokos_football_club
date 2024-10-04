package com.dpds.kokos_football_club.user

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class UserRequest (
    @JsonProperty("login")
    @Size(max = 200)
    val login: String,
    @JsonProperty("password")
    @Size(min = 6, max = 32)
    val password: String,
    @JsonProperty("first_name")
    @Size(max = 200)
    val firstName: String,
    @JsonProperty("last_name")
    @Size(max = 200)
    val lastName: String,
    @JsonProperty("email")
    @Size(max = 200)
    @Email
    val email: String,
    @JsonProperty("age")
    val age: Int,
)

data class UserResponse (
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("login")
    val login: String,
    @JsonProperty("full_name")
    val fullName: String,
    @JsonProperty("email")
    val email: String,
    @JsonProperty("age")
    val age: Int,
) {
    constructor(user: User): this (
        id = user.id,
        login = user.login,
        fullName = user.firstName + " " + user.lastName,
        email = user.email,
        age = user.age,
    )
}

data class UpdateUserRequest (
    @JsonProperty("first_name")
    @Size(max = 200)
    val firstName: String,
    @JsonProperty("last_name")
    @Size(max = 200)
    val lastName: String,
    @JsonProperty("email")
    @Size(max = 200)
    val email: String,
    @JsonProperty("age")
    val age: Int,
)

data class ChangePasswordRequest (
    @JsonProperty("password")
    @Size(min = 6, max = 32)
    val password: String
)